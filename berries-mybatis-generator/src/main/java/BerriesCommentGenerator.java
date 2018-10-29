import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;

public class BerriesCommentGenerator implements CommentGenerator {

	private Properties properties;
	private Properties systemPro;
	private boolean suppressDate;
	private boolean suppressAllComments;
	private String currentDateStr;

	public BerriesCommentGenerator() {
	    super();
	    properties = new Properties();
	    systemPro = System.getProperties();
	    suppressDate = false;
	    suppressAllComments = false;
	    currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
	}

	public void addJavaFileComment(CompilationUnit compilationUnit) {
		StringBuilder sb = new StringBuilder();

		compilationUnit.addFileCommentLine("/**");
	    sb.append(" * ");
	    sb.setLength(0);
	    sb.append(" * @author ");
	    sb.append(systemPro.getProperty("user.name"));
	    sb.append(" ");
	    sb.append(currentDateStr);
	    compilationUnit.addFileCommentLine(sb.toString());
	    compilationUnit.addFileCommentLine(" */");
	    return;
	}

	/**
	 * Adds a suitable comment to warn users that the element was generated, and
	 * when it was generated.
	 */
	public void addComment(XmlElement xmlElement) {
	    return;
	}

	public void addRootComment(XmlElement rootElement) {
	    // add no document level comments by default
	    return;
	}

	public void addConfigurationProperties(Properties properties) {
	    this.properties.putAll(properties);

	    suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

	    suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
	}

	/**
	 * This method adds the custom javadoc tag for. You may do nothing if you do
	 * not wish to include the Javadoc tag - however, if you do not include the
	 * Javadoc tag then the Java merge capability of the eclipse plugin will
	 * break.
	 * 
	 * @param javaElement
	 *            the java element
	 */
	protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
	    javaElement.addJavaDocLine(" *");
	    StringBuilder sb = new StringBuilder();
	    sb.append(" * ");
	    sb.append(MergeConstants.NEW_ELEMENT_TAG);
	    if (markAsDoNotDelete) {
	        sb.append(" do_not_delete_during_merge");
	    }
	    String s = getDateString();
	    if (s != null) {
	        sb.append(' ');
	        sb.append(s);
	    }
	    javaElement.addJavaDocLine(sb.toString());
	}

	/**
	 * This method returns a formated date string to include in the Javadoc tag
	 * and XML comments. You may return null if you do not want the date in
	 * these documentation elements.
	 * 
	 * @return a string representing the current timestamp, or null
	 */
	protected String getDateString()  {
	    String result = null;
	    if (!suppressDate) {
	        result = currentDateStr;
	    }
	    return result;
	}

	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
	    if (suppressAllComments) {
	        return;
	    }
	    StringBuilder sb = new StringBuilder();
	    innerClass.addJavaDocLine("/**");
	    sb.append(" * ");
	    sb.append(introspectedTable.getFullyQualifiedTable());
	    sb.append(" ");
	    sb.append(getDateString());
	    innerClass.addJavaDocLine(sb.toString());
	    innerClass.addJavaDocLine(" */");
	    
	}

	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
	    if (suppressAllComments) {
	        return;
	    }

	    StringBuilder sb = new StringBuilder();

	    innerEnum.addJavaDocLine("/**");
	    //      addJavadocTag(innerEnum, false);
	    sb.append(" * ");
	    sb.append(introspectedTable.getFullyQualifiedTable());
	    innerEnum.addJavaDocLine(sb.toString());
	    innerEnum.addJavaDocLine(" */");
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable,
	        IntrospectedColumn introspectedColumn) {
	    if (suppressAllComments) {
	        return;
	    }

	    StringBuilder sb = new StringBuilder();

	    field.addJavaDocLine("/**");
	    sb.append(" * ");
	    sb.append(introspectedColumn.getRemarks());
	    field.addJavaDocLine(sb.toString());

	    //      addJavadocTag(field, false);

	    field.addJavaDocLine(" */");
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
	    if (suppressAllComments) {
	        return;
	    }

	    StringBuilder sb = new StringBuilder();

	    field.addJavaDocLine("/**");
	    sb.append(" * ");
	    sb.append(introspectedTable.getFullyQualifiedTable());
	    field.addJavaDocLine(sb.toString());
	    field.addJavaDocLine(" */");
	}

	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
	    if (suppressAllComments) {
	        return;
	    }
	    //      method.addJavaDocLine("/**");
	    //      addJavadocTag(method, false);
	    //      method.addJavaDocLine(" */");
	}

	public void addGetterComment(Method method, IntrospectedTable introspectedTable,
	        IntrospectedColumn introspectedColumn) {
	    if (suppressAllComments) {
	        return;
	    }

	    method.addJavaDocLine("/**");

	    StringBuilder sb = new StringBuilder();
	    sb.append(" * ");
	    sb.append(introspectedColumn.getRemarks());
	    method.addJavaDocLine(sb.toString());

	    sb.setLength(0);
	    sb.append(" * @return ");
	    sb.append(introspectedColumn.getActualColumnName());
	    sb.append(" ");
	    sb.append(introspectedColumn.getRemarks());
	    method.addJavaDocLine(sb.toString());

	    //      addJavadocTag(method, false);

	    method.addJavaDocLine(" */");
	    
	    String columnName = introspectedColumn.getActualColumnName();
	    boolean isPrimaryKey = false;
	    for(IntrospectedColumn primaryKeyColumns : introspectedTable.getPrimaryKeyColumns()) {
	    	if(primaryKeyColumns.getActualColumnName().equals(columnName)) {
	    		isPrimaryKey = true;
	    		break;
	    	}
	    }
	    if(isPrimaryKey) {
	    	method.addAnnotation("@Id(name = \"" + introspectedColumn.getActualColumnName() + "\")");
	    }
	    else {
	    	method.addAnnotation("@Column(name = \"" + introspectedColumn.getActualColumnName() + "\")");
	    }
	}

	public void addSetterComment(Method method, IntrospectedTable introspectedTable,
	        IntrospectedColumn introspectedColumn) {
	    if (suppressAllComments) {
	        return;
	    }


	    method.addJavaDocLine("/**");
	    StringBuilder sb = new StringBuilder();
	    sb.append(" * ");
	    sb.append(introspectedColumn.getRemarks());
	    method.addJavaDocLine(sb.toString());

	    Parameter parm = method.getParameters().get(0);
	    sb.setLength(0);
	    sb.append(" * @param ");
	    sb.append(parm.getName());
	    sb.append(" ");
	    sb.append(introspectedColumn.getRemarks());
	    method.addJavaDocLine(sb.toString());

	    //      addJavadocTag(method, false);

	    method.addJavaDocLine(" */");
	}

	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
	    if (suppressAllComments) {
	        return;
	    }

	    StringBuilder sb = new StringBuilder();

	    innerClass.addJavaDocLine("/**");
	    sb.append(" * ");
	    sb.append(introspectedTable.getFullyQualifiedTable());
	    innerClass.addJavaDocLine(sb.toString());

	    sb.setLength(0);
	    sb.append(" * @author ");
	    sb.append(systemPro.getProperty("user.name"));
	    sb.append(" ");
	    sb.append(currentDateStr);

	    //      addJavadocTag(innerClass, markAsDoNotDelete);

	    innerClass.addJavaDocLine(" */");
	}
	
	private boolean isTrue(String str) {
		if(str != null && str.equalsIgnoreCase("true")){
			return true;
		}
		return false;
	}

	
	public void addModelClassComment(TopLevelClass topClass, IntrospectedTable introspectedTable) {
		topClass.addJavaDocLine("/**");
    	topClass.addJavaDocLine(" * " + introspectedTable.getRemarks());
    	topClass.addJavaDocLine(" */");
    	
    	topClass.addImportedType("com.lee.berries.dao.annotation.Id");
		topClass.addImportedType("com.lee.berries.dao.annotation.Entity");
		topClass.addImportedType("com.lee.berries.dao.annotation.Column");
    	topClass.addAnnotation("@Entity(tableName = \"" + introspectedTable.getFullyQualifiedTable().getIntrospectedTableName() + "\")");
    }

	
	public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
			Set<FullyQualifiedJavaType> imports) {
		// TODO Auto-generated method stub
		
	}

	
	public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
		// TODO Auto-generated method stub
		
	}

	
	public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
			Set<FullyQualifiedJavaType> imports) {
		// TODO Auto-generated method stub
		
	}

	
	public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
			IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> imports) {
		// TODO Auto-generated method stub
		
	}

	
	public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable,
			Set<FullyQualifiedJavaType> imports) {
		// TODO Auto-generated method stub
		
	}
}
