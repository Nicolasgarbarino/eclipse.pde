package org.eclipse.pde.internal.editor.schema;
/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved.
 */

import org.eclipse.pde.internal.base.model.*;
import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.dialogs.*;
import org.eclipse.swt.events.*;
import org.eclipse.pde.internal.elements.*;
import org.eclipse.pde.internal.base.schema.*;
import org.eclipse.pde.internal.schema.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.pde.internal.forms.*;
import org.eclipse.jface.text.source.*;
import org.eclipse.swt.*;
import org.eclipse.jface.resource.*;
import org.eclipse.pde.internal.editor.text.*;
import org.eclipse.jface.text.presentation.*;
import org.eclipse.pde.internal.editor.*;
import org.eclipse.jface.text.*;
import org.eclipse.pde.internal.PDEProblemFinder;
import org.eclipse.pde.internal.PDEPlugin;

public class DescriptionSection extends PDEFormSection {
	private SourceViewer editor;
	private Button applyButton;
	private Button resetButton;
	private IDocument document;
	private boolean editable = true;
	private SourceViewerConfiguration sourceConfiguration;
	private ISchemaObject element;
	private SourceViewer sourceViewer;
	public static final String SECTION_TITLE  = "SchemaEditor.DescriptionSection.title";
	public static final String KEY_PENDING_TITLE  = "SchemaEditor.DescriptionSection.pending.title";
	public static final String KEY_APPLY  = "Actions.apply.label";
	public static final String KEY_RESET  = "Actions.reset.label";
	public static final String KEY_PENDING_MESSAGE  = "SchemaEditor.DescriptionSection.pending.message";
	public static final String SECTION_DESC  = "SchemaEditor.DescriptionSection.desc";
	private IDocumentPartitioner partitioner;
	private ISchema schema;
	private boolean ignoreChange=false;
	private IColorManager colorManager;

public DescriptionSection(PDEFormPage page, IColorManager colorManager) {
	super(page);
	setHeaderText(PDEPlugin.getResourceString(SECTION_TITLE));
	setDescription(PDEPlugin.getResourceString(SECTION_DESC));
	this.colorManager = colorManager;
	sourceConfiguration = new XMLConfiguration(colorManager);
	document = new Document();
	partitioner =
		new RuleBasedPartitioner(
			new PDEPartitionScanner(),
			new String[] { PDEPartitionScanner.XML_TAG, PDEPartitionScanner.XML_COMMENT });
	partitioner.connect(document);
	document.setDocumentPartitioner(partitioner);
}
private void checkForPendingChanges() {
	if (applyButton.isEnabled()) {
		if (MessageDialog
			.openQuestion(
				PDEPlugin.getActiveWorkbenchShell(),
				PDEPlugin.getResourceString(KEY_PENDING_TITLE),
				PDEPlugin.getResourceString(KEY_PENDING_MESSAGE))
			== true)
			handleApply();
	}
}
public void commitChanges(boolean onSave) {
	handleApply();
	setDirty(false);
	resetButton.setEnabled(false);
}
public Composite createClient(Composite parent, FormWidgetFactory factory) {
	Composite container = factory.createComposite(parent);
	GridLayout layout = new GridLayout();
	layout.numColumns = 2;
	container.setLayout(layout);
	GridData gd;
	int styles = SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | factory.BORDER_STYLE;
	sourceViewer = new SourceViewer(container, null, styles);
	sourceViewer.configure(sourceConfiguration);
	sourceViewer.setDocument(document);
	sourceViewer.setEditable(isEditable());
	Control styledText= sourceViewer.getTextWidget();
	styledText.setFont(JFaceResources.getFontRegistry().get(JFaceResources.TEXT_FONT));
	if (SWT.getPlatform().equals("motif")==false)
	   factory.paintBordersFor(container);
	Control [] children = container.getChildren();
	Control control = children[children.length-1];
	gd = new GridData(GridData.FILL_BOTH);
    gd.heightHint = 64;
	control.setLayoutData(gd);
	
	Composite buttonContainer = factory.createComposite(container);
	layout = new GridLayout();
	layout.marginHeight = 0;
	buttonContainer.setLayout(layout);
	gd = new GridData(GridData.FILL_VERTICAL);
	buttonContainer.setLayoutData(gd);
	
	applyButton = factory.createButton(buttonContainer, PDEPlugin.getResourceString(KEY_APPLY), SWT.PUSH);
	applyButton.setEnabled(false);
	gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING);
	applyButton.setLayoutData(gd);
	applyButton.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			handleApply();
		}
	});

	resetButton = factory.createButton(buttonContainer, PDEPlugin.getResourceString(KEY_RESET), SWT.PUSH);
	resetButton.setEnabled(false);
	gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING);
	resetButton.setLayoutData(gd);
	resetButton.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			handleReset();
		}
	});
	return container;
}
public void doGlobalAction(String actionId) {
	PDEProblemFinder.fixMe("Global operation mapping must be done better");
	if (actionId.equals(org.eclipse.ui.IWorkbenchActionConstants.CUT)) {
		sourceViewer.doOperation(sourceViewer.CUT);
	}
	else if (actionId.equals(org.eclipse.ui.IWorkbenchActionConstants.COPY)) {
		sourceViewer.doOperation(sourceViewer.COPY);
	}
	else if (actionId.equals(org.eclipse.ui.IWorkbenchActionConstants.PASTE)) {
		sourceViewer.doOperation(sourceViewer.PASTE);
	}
	else if (actionId.equals(org.eclipse.ui.IWorkbenchActionConstants.DELETE)) {
		sourceViewer.doOperation(sourceViewer.DELETE);
	}
	else if (actionId.equals(org.eclipse.ui.IWorkbenchActionConstants.UNDO)) {
		sourceViewer.doOperation(sourceViewer.UNDO);
	}
	else if (actionId.equals(org.eclipse.ui.IWorkbenchActionConstants.REDO)) {
		sourceViewer.doOperation(sourceViewer.REDO);
	}
}
private void handleApply() {
	if (element != null)
		((SchemaObject)element).setDescription(document.get());
	applyButton.setEnabled(false);
}
private void handleReset() {
	updateDocument();
}
public void initialize(Object input) {
	schema = (ISchema) input;
	document.addDocumentListener(new IDocumentListener() {
		public void documentChanged(DocumentEvent e) {
			if (!ignoreChange && schema instanceof IEditable) {
				setDirty(true);
				((IEditable)schema).setDirty(true);
				getFormPage().getEditor().fireSaveNeeded();
			}
			applyButton.setEnabled(true);
			resetButton.setEnabled(true);
		}
		public void documentAboutToBeChanged(DocumentEvent e) {
		}
	});
}
public boolean isEditable() {
	return editable;
}
public void sectionChanged(
	FormSection source,
	int changeType,
	Object changeObject) {
	checkForPendingChanges();
	if (!(source instanceof ElementSection))
		return;
	if (changeType != FormSection.SELECTION)
		return;
	element = (ISchemaObject) changeObject;
	if (element == null)
		element = schema;
	updateDocument();
}
public void setEditable(boolean newEditable) {
	editable = newEditable;
}
public void updateDocument() {
	ignoreChange = true;
	String text = element.getDescription();
	if (text == null)
		text = "";
	else
		text = TextUtil.createMultiLine(text, 60, false);
	document.set(text);
	resetButton.setEnabled(false);
	applyButton.setEnabled(false);
	ignoreChange = false;
}
}
