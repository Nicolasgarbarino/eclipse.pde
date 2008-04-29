/*******************************************************************************
 * Copyright (c) 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.pde.api.tools.internal.provisional.problems;


/**
 * Describes a given api problem.
 * 
 * @since 1.0.0
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IApiProblem {

	/**
	 * Constant representing the incompatibility problem category 
	 */
	public static final int CATEGORY_COMPATIBILITY = 0x10000000;
	
	/**
	 * Constant representing the api usage problem category
	 */
	public static final int CATEGORY_USAGE = 0x20000000;
	
	/**
	 * Constant representing the version problem category
	 */
	public static final int CATEGORY_VERSION = 0x30000000;
	
	/**
	 * Constant representing the since tag problem category
	 */
	public static final int CATEGORY_SINCETAGS = 0x40000000;
	
	/**
	 * Constant representing the api profile problem category
	 */
	public static final int CATEGORY_API_PROFILE = 0x50000000;
	
	/**
	 * Constant representing the offset of the message key portion of the id bit mask.
	 */
	public static final int OFFSET_MESSAGE = 0;
	
	/**
	 * Constant representing the offset of the flags portion of a problem id bit mask.
	 */
	public static final int OFFSET_FLAGS = 12;
	
	/**
	 * Constant representing the offset of the kinds portion of a problem id bit mask.
	 */
	public static final int OFFSET_KINDS = 20;
	
	/**
	 * Constant representing the offset of the element kinds portion of a problem id bit mask.
	 */
	public static final int OFFSET_ELEMENT = 24;
	
	/**
	 * Constant representing the value of having no flags.
	 * Value is: <code>0</code>
	 */
	public static final int NO_FLAGS = 0x0;

	/**
	 * Constant representing the value of the invalid @since tag {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>1</code>
	 * 
	 * @see #getKind()
	 */
	public static final int SINCE_TAG_INVALID = 1;

	/**
	 * Constant representing the value of the malformed @since tag {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>2</code>
	 * 
	 * @see #getKind()
	 */
	public static final int SINCE_TAG_MALFORMED = 2;

	/**
	 * Constant representing the value of the missing @since tag {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>3</code>
	 * 
	 * @see #getKind()
	 */
	public static final int SINCE_TAG_MISSING = 3;

	/**
	 * Constant representing the value of the major version change {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>1</code>
	 * 
	 * @see #getKind()
	 */
	public static final int MAJOR_VERSION_CHANGE = 1;

	/**
	 * Constant representing the value of the minor version change {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>2</code>
	 * 
	 * @see #getKind()
	 */
	public static final int MINOR_VERSION_CHANGE = 2;
	
	/**
	 * Constant representing the value of the major version change (no API breakage) {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>3</code>
	 * 
	 * @see #getKind()
	 */
	public static final int MAJOR_VERSION_CHANGE_NO_BREAKAGE = 3;
	
	/**
	 * Constant representing the value of an illegal extend {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>1</code>
	 * 
	 * @see #getKind()
	 */
	public static final int ILLEGAL_EXTEND = 1;
	
	/**
	 * Constant representing the value of an illegal instantiate {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>2</code>
	 * 
	 * @see #getKind()
	 */
	public static final int ILLEGAL_INSTANTIATE = 2;
	
	/**
	 * Constant representing the value of an illegal reference {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>3</code>
	 * 
	 * @see #getKind()
	 */
	public static final int ILLEGAL_REFERENCE = 3;
	
	/**
	 * Constant representing the value of an illegal implement {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>4</code>
	 * 
	 * @see #getKind()
	 */
	public static final int ILLEGAL_IMPLEMENT = 4;
	
	/**
	 * Constant representing the value of an illegal override {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>5</code>
	 * 
	 * @see #getKind()
	 */
	public static final int ILLEGAL_OVERRIDE = 5;
	
	/**
	 * Constant representing the value of an API leak {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>6</code>
	 * 
	 * @see #getKind()
	 */
	public static final int API_LEAK = 6;	
	
	/**
	 * Constant representing the value of an invalid API javadoc tag use {@link IApiProblem} kind
	 * <br>
	 * Value is: <code>7</code>
	 * 
	 *  @see #getKind()
	 */
	public static final int UNSUPPORTED_TAG_USE = 7;
	
	/**
	 * Flags to indicate a leak from extending a non-API type. 
	 * <br>
	 * Value is: <code>1</code>
	 * 
	 * @see #getFlags()
	 */
	public static final int LEAK_EXTENDS = 1;	
	
	/**
	 * Flags to indicate a leak from implementing a non-API type. 
	 * <br>
	 * Value is: <code>2</code>
	 * 
	 * @see #getFlags()
	 */
	public static final int LEAK_IMPLEMENTS = 2;
	
	/**
	 * Flags to indicate a leak from a field declaration. 
	 * <br>
	 * Value is: <code>3</code>
	 * 
	 * @see #getFlags()
	 */
	public static final int LEAK_FIELD = 3;
	
	/**
	 * Flags to indicate a leak from a return type. 
	 * <br>
	 * Value is: <code>4</code>
	 * 
	 * @see #getFlags()
	 */
	public static final int LEAK_RETURN_TYPE = 4;
	
	/**
	 * Flags to indicate a leak from a method parameter 
	 * <br>
	 * Value is: <code>5</code>
	 * 
	 * @see #getFlags()
	 */
	public static final int LEAK_METHOD_PARAMETER = 5;	
	
	/**
	 * Flags to indicate a leak from a constructor parameter 
	 * <br>
	 * Value is: <code>6</code>
	 * 
	 * @see #getFlags()
	 */
	public static final int LEAK_CONSTRUCTOR_PARAMETER = 6;
	
	/**
	 * Flags to indicate a constructor method
	 * <br>
	 * Value is: <code>7</code>
	 * 
	 * @see #getFlags()
	 */
	public static final int CONSTRUCTOR_METHOD = 7;
	
	/**
	 * Flags to indicate a 'normal' method
	 * <br>
	 * Value is: <code>8</code>
	 * 
	 * @see #getFlags()
	 */
	public static final int METHOD = 8;

	/**
	 * Flags to indicate a field
	 * <br>
	 * Value is: <code>9</code>
	 * 
	 * @see #getFlags()
	 */
	public static final int FIELD = 9;

	/**
	 * Constant representing the value of a default API profile {@link IApiProblem} kind.
	 * <br>
	 * Value is: <code>1</code>
	 * 
	 * @see #getKind()
	 */
	public static final int API_PROFILE_MISSING = 1;
	
	/**
	 * Returns the severity of the problem. 
	 * See {@link IMarker} for a listing of severities.
	 * 
	 * @return the severity of the problem
	 */
	public int getSeverity();
	
	/**
	 * Returns the kind of element this problem is related to.
	 * 
	 * @see IElementDescriptor#getElementType()
	 * @see IDelta#getElementType()
	 * 
	 * @return the element kind this problem is related to.
	 */
	public int getElementKind();
	
	/**
	 * Returns the id used to lookup the message for this problem.
	 * 
	 * @return the message id
	 */
	public int getMessageid();
	
	/**
	 * Returns the project relative path to the resource this problem 
	 * was found in.
	 * 
	 * @return the project relative path to the resource the problem was found in
	 */
	public String getResourcePath();
	
	/**
	 * Returns the listing of message arguments passed in to the problem or an
	 * empty array, never <code>null</code>
	 * 
	 * @return the message arguments passed to the problem or an empty array
	 */
	public String[] getMessageArguments();
	
	/**
	 * Returns the start of the character selection to make, or -1 if there is no character 
	 * starting position.
	 * 
	 * @return the start of the character selection or -1.
	 */
	public int getCharStart();
	
	/**
	 * Returns the end of the character selection to make, or -1 if there is no character 
	 * ending position.
	 * 
	 * @return the end of the character selection or -1
	 */
	public int getCharEnd();
	
	/**
	 * Returns the number of the line this problem occurred on, or -1 
	 * if there is no line number.
	 * 
	 * @return the line number this problem occurred on or -1
	 */
	public int getLineNumber();
	
	/**
	 * Returns the category for this problem. Guaranteed to be
	 * one of:
	 * <ul>
	 * <li>{@link #CATEGORY_COMPATIBILITY}</li>
	 * <li>{@link #CATEGORY_SINCETAGS}</li>
	 * <li>{@link #CATEGORY_USAGE}</li>
	 * <li>{@link #CATEGORY_VERSION}</li>
	 * <li>{@link #CATEGORY_API_PROFILE}</li>
	 * </ul> 
	 * @return the category for the problem
	 */
	public int getCategory();
	
	/**
	 * Returns the unique id of the problem. A problem Id is the composition of
	 * the category, kind, flags and severity.
	 * 
	 * @return the id of the problem
	 */
	public int getId();
	
	/**
	 * Returns a human readable, localized description of the problem
	 * 
	 * @return the description of the problem
	 */
	public String getMessage();
	
	/**
	 * Returns the kind of this problem.
	 * 
	 * @return the kind of this problem
	 */
	public int getKind();
	
	/**
	 * Returns the flags for this problem. 
	 * 
	 * @return the flags for this problem
	 */
	public int getFlags();
	
	/**
	 * Returns the names of the extra marker attributes associated to this problem when persisted into a marker 
	 * by the {@link ApiProblemReporter}. By default, no EXTRA attributes is persisted, and an 
	 * {@link IApiProblem} only persists the following attributes:
	 * <ul>
	 * <li>	<code>IMarker#MESSAGE</code> -&gt; {@link IApiProblem#getMessage()}</li>
	 * <li>	<code>IMarker#SEVERITY</code> -&gt; {@link IApiProblem#getSeverity()} </li>
	 * <li>	<code>IApiMarkerConstants#API_MARKER_ATTR_ID : String</code> -&gt; {@link IApiProblem#getId()}</li>
	 * <li>	<code>IMarker#CHAR_START</code>  -&gt; {@link IApiProblem#getCharStart()}</li>
	 * <li>	<code>IMarker#CHAR_END</code>  -&gt; {@link IApiProblem#getCharEnd()}</li>
	 * <li>	<code>IMarker#LINE_NUMBER</code>  -&gt; {@link IApiProblem#getLineNumber()}</li>
	 * </ul>
	 * The names must be eligible for marker creation, as defined by <code>IMarker#setAttributes(String[], Object[])</code>, 
	 * and there must be as many names as values according to {@link #getExtraMarkerAttributeValues()}.
	 * Note that extra marker attributes will be inserted after default ones (as described in {@link CategorizedProblem#getMarkerType()},
	 * and thus could be used to override defaults.
	 * @return the names of the corresponding marker attributes
	 */
	public String[] getExtraMarkerAttributeIds();
	
	/**
	 * Returns the respective values for the extra marker attributes associated to this problem when persisted into 
	 * a marker by the JavaBuilder. Each value must correspond to a matching attribute name, as defined by
	 * {@link #getExtraMarkerAttributeIds()}. 
	 * The values must be eligible for marker creation, as defined by <code> IMarker#setAttributes(String[], Object[])}.
	 * @return the values of the corresponding extra marker attributes
	 */
	public Object[] getExtraMarkerAttributeValues();
}
