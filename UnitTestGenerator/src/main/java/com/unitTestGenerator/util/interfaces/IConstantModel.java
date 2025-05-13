package com.unitTestGenerator.util.interfaces;

import com.unitTestGenerator.pojos.Clase;
import com.unitTestGenerator.util.DetectSO;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public interface IConstantModel {


    public final static String BREAK_LINE = System.lineSeparator();
    public final static String pathSeparator = java.nio.file.FileSystems.getDefault().getSeparator();
    public final static String Separator = DetectSO.os().equals("win")? "\\\\":File.separator;
    public final static String PDF_Extention = ".pdf";
    public static final String PRIVATE = "private";
    public static final String LISTAS = "List<";
    public static final String ARRAY = "[]";
    public static final String JAVA_BASE_PAHT = pathSeparator + "src"+pathSeparator+"main"+pathSeparator+"java";
    public final String DOUBLEBREAK_LINE = "\r\n"+"\r\n";
    public final String TAB = "\t";
    public final String DOUBLETAB = "\t\t";
    public static final String PUBLIC = "public";
    public static final String CLASS = "class";
    public static final String RETURN = "return";
    public static final String VOID = "void";
    public static final String AUTOWIRED = "@Autowired";
    public static final String PARENTHESES_OPEN = "(";
    public static final String PARENTHESES_CLOSE = ")";
    public static final String PARENTHESES_OPEN_CLOSE = "()";
    public static final String openAngleBrackets = "<";
    public static final String closeAngleBrackets = ">";
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String SEMICOLON = ";";
    public static final String BRACKET_OPEN = "{";
    public static final String BRACKET_CLOSE = "}";
    public static final String BRACKET_OPEN_CLOSE = "{}";
    public static final String LINE_SPACE_REGEX = "(?m)^( {0,3})";
    public static final String DOT = ".";
    public static final String PACKAGE_CONST = "package";
    public static final String IMPORT_CONST = "import";
    public static final String TRY = "try";
    public static final String CATCH = "catch";
    public static final String EQUAL_ ="=";
    public static final String NOT_NULL =" != null";
    public static final String IF_STAMENT ="if";
    public static final String ELSE_STAMENT="else";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String DESCRIPTION= "description";


    public final String GSON_IMPORT 										= "import com.google.gson.Gson;"+ BREAK_LINE;

    public final String MODELMAPPER_IMPORT     								= "import org.modelmapper.ModelMapper;"+ BREAK_LINE;

    public final String NET_IMPORT 											= "import java.net.*;"+ BREAK_LINE;
    public final String NET_INETADDRESS_IMPORT 								= "import java.net.InetAddress;"+ BREAK_LINE;
    public final String NET_UNKNOWN_EXCEPTION_IMPORT 						= "import java.net.UnknownHostException;"+ BREAK_LINE;
    public final String NET_LIGALA_ZIP4J_ZIP_FILE_IMPORT 					= "import net.lingala.zip4j.core.ZipFile;"+ BREAK_LINE;
    public final String NET_LIGALA_ZIP4J_ZIP_EXCEPTION_IMPORT 				= "import net.lingala.zip4j.exception.ZipException;"+ BREAK_LINE;
    public final String NET_LIGALA_ZIP4J_ZIP_PARAMETERS_IMPORT 				= "import net.lingala.zip4j.model.ZipParameters;"+ BREAK_LINE;
    public final String NET_LIGALA_ZIP4J_ZIP_CONSTANTS_IMPORT 				= "import net.lingala.zip4j.util.Zip4jConstants;"+ BREAK_LINE;

    public final String UTIL_LIST_IMPORT       								= "import java.util.List;"+ BREAK_LINE ;
    public final String UTIL_DATE_IMPORT       								= "import java.util.Date;"+ BREAK_LINE;
    public final String UTIL_ARRAY_LIST_IMPORT 								= "import java.util.ArrayList;"+ BREAK_LINE;
    public final String UTIL_OBJECTS_IMPORT 								= "import java.util.Objects;"+ BREAK_LINE;
    public final String UTIL_OPTIONAL_IMPORT 								= "import java.util.Optional;"+ BREAK_LINE;
    public final String UTIL_PATTERN_IMPORT 								= "import java.util.regex.Pattern;"+ BREAK_LINE;
    public final String UTIL_ITERATOR 										= "import java.util.Iterator;" + BREAK_LINE;
    public final String UTIL_LOGGIN_LEVEL_IMPORT 							= "import java.util.logging.Level;"+ BREAK_LINE;
    public final String UTIL_LOGGIN_LOGGER_IMPORT 							= "import java.util.logging.Logger;"+ BREAK_LINE;


    public final String SPRING_ANNOTATION_IMPORT      						= "import org.springframework.web.bind.annotation.*;"+ BREAK_LINE;
    public final String SPRING_ANNOTATION_CrossOrigin_IMPORT  				= "import org.springframework.web.bind.annotation.CrossOrigin;" + BREAK_LINE;
    public final String SPRING_ANNOTATION_GetMapping_IMPORT					= "import org.springframework.web.bind.annotation.GetMapping;"  + BREAK_LINE;
    public final String SPRING_ANNOTATION_RequestMapping_IMPORT				= "import org.springframework.web.bind.annotation.RequestMapping;" + BREAK_LINE;
    public final String SPRING_ANNOTATION_RestController_IMPORT				= "import org.springframework.web.bind.annotation.RestController;" + BREAK_LINE;
    public final String SPRING_AUTOWIRED_IMPORT      						= "import org.springframework.beans.factory.annotation.Autowired;"+ BREAK_LINE ;
    public final String SPRING_COMPONENT_IMPORT       						= "import org.springframework.stereotype.Component;"+ BREAK_LINE;
    public final String SPRING_DATAACCESS_EXCEPTION_IMPORT 					= "import org.springframework.dao.DataAccessException;"+ BREAK_LINE;
    public final String SPRING_STEREOTYPE_SERVICE_IMPORT 					= "import org.springframework.stereotype.Service;"+ BREAK_LINE;
    public final String SPRING_CONTEX_ANNOTATION_SCOPE_IMPORT 				= "import org.springframework.context.annotation.Scope;" + BREAK_LINE;
    public final String SPRING_CONTEX_ANNOTATION_BEAN_IMPORT  				= "import org.springframework.context.annotation.Bean;"+ BREAK_LINE;
    public final String SPRING_CONTEX_ANNOTATION_CONFIGURATION_IMPORT  		= "import org.springframework.context.annotation.Configuration;"+ BREAK_LINE;
    public final String SPRING_RESPONSE_ENTITY_IMPORT 						= "import org.springframework.http.ResponseEntity;"+ BREAK_LINE;
    public final String SPRING_HTTP_STATUS_IMPORT 							= "import org.springframework.http.HttpStatus;"+ BREAK_LINE;

    public final String SPRING_CRUD_REPOSITORY_IMPORT 						= "import org.springframework.data.repository.CrudRepository;"+ BREAK_LINE;
    public final String SPRING_JPA_REPOSITORY_QUERY_IMPORT_PARAM 			= "import org.springframework.data.repository.query.Param;"+ BREAK_LINE;

    public final String SPRING_JPA_REPOSITORY_IMPORT 						= "import org.springframework.data.jpa.repository.JpaRepository;"+ BREAK_LINE;
    public final String SPRING_JPA_REPOSITORY_QUERY_IMPORT 					= "import org.springframework.data.jpa.repository.Query;"+ BREAK_LINE;

    public final String SPRING_BOOT_SpringApplicationBuilder_IMPORT 		= "import org.springframework.boot.builder.SpringApplicationBuilder;"+ BREAK_LINE;
    public final String SPRING_BOOT_support_SpringBootServletInitializer_IMPORT = "import org.springframework.boot.web.support.SpringBootServletInitializer;"+ BREAK_LINE;
    public final String SPRING_BOOT_SpringBootServletInitializer_IMPORT		= "import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;"+ BREAK_LINE;


    public final String SPRING_BOOT_TEST_IMPORT 							= "import org.springframework.boot.test.context.SpringBootTest;"+ BREAK_LINE;
    public final String SPRING_BOOT_SpringApplication_IMPORT 				= "import org.springframework.boot.SpringApplication;"+ BREAK_LINE;
    public final String SPRING_BOOT_SpringBootApplication_IMPORT 			= "import org.springframework.boot.autoconfigure.SpringBootApplication;"+ BREAK_LINE;


    public final String SPRINGFOX_PathSelectors_IMPORT 						= "import springfox.documentation.builders.PathSelectors;"+ BREAK_LINE;
    public final String SPRINGFOX_RequestHandlerSelectors_IMPORT 			= "import springfox.documentation.builders.RequestHandlerSelectors;"+ BREAK_LINE;
    public final String SPRINGFOX_DocumentationType_IMPORT 					= "import springfox.documentation.spi.DocumentationType;"+ BREAK_LINE;
    public final String SPRINGFOX_Docket_IMPORT 							= "import springfox.documentation.spring.web.plugins.Docket;"+ BREAK_LINE;
    public final String SPRINGFOX_EnableSwagger2_IMPORT 					= "import springfox.documentation.swagger2.annotations.EnableSwagger2;"+ BREAK_LINE;
    public final String SPRINGFOX_ApiInfoBuilder_IMPORT 					=  "import springfox.documentation.builders.ApiInfoBuilder;"+ BREAK_LINE;
    public final String SPRINGFOX_ApiInfo_IMPORT 							= "import springfox.documentation.service.ApiInfo;"+ BREAK_LINE;
    public final String SPRINGFOX_Contact_IMPORT 							= "import springfox.documentation.service.Contact;"+ BREAK_LINE;

    public final String JAVAX_PERSISTENCE_IMPORT 							= "import javax.persistence.*;"+ BREAK_LINE;
    public final String JAVAX_SWING_IMPORT 									= "import javax.swing.*;"+ BREAK_LINE;
    public final String JAVAX_XML_DATA_TYPE_CONVERTER_IMPORT 				= "import javax.xml.bind.DatatypeConverter;"+ BREAK_LINE;

    public final String SLF4J_LOGGER_IMPORT 								= "import org.slf4j.Logger;"+ BREAK_LINE;
    public final String SLF4J_LOGGER_FACTORY_IMPORT 						= "import org.slf4j.LoggerFactory;"+ BREAK_LINE;

    public final String IO_IMPORT 											= "import java.io.*;" + BREAK_LINE;
    public final String IO_SEREABILIZABLE_IMPORT 							= "import java.io.Serializable;"+ BREAK_LINE;
    public final String IO_BUFFER_READER_IMPORT 							= "import java.io.BufferedReader;"+ BREAK_LINE;
    public final String IO_IOEXCEPTION_IMPORT 								= "import java.io.IOException;"+ BREAK_LINE;
    public final String IO_IMPUT_STREAM_READER_IMPORT 						= "import java.io.InputStreamReader;"+ BREAK_LINE;
    public final String IO_FILE_IMPORT 										= "import java.io.File;"+ BREAK_LINE;
    public final String IO_FILE_INPUT_STREAM_IMPORT 						="import java.io.FileInputStream;"+ BREAK_LINE;
    public final String IO_FILE_NOTFOUND_EXCEPTION 							= "import java.io.FileNotFoundException;" + BREAK_LINE;
    public final String IO_FILE_OUTPUT_STREAM_IMPORT 						= "import java.io.FileOutputStream;"  + BREAK_LINE;

    public final String APACHE_LOG_IMPORT 									= "import org.apache.commons.logging.Log;"+ BREAK_LINE;
    public final String APACHE_LOG_FACTORY_IMPORT			 				= "import org.apache.commons.logging.LogFactory;"+ BREAK_LINE;

    public final String APACHE_POI_SS_CELL_IMPORT 							= "import org.apache.poi.ss.usermodel.Cell;"+ BREAK_LINE;
    public final String APACHE_POI_SS_ROW_IMPORT							= "import org.apache.poi.ss.usermodel.Row;" + BREAK_LINE;
    public final String APACHE_POI_SS_CELL_STYLE_IMPORT						= "import org.apache.poi.ss.usermodel.CellStyle;" + BREAK_LINE;
    public final String APACHE_POI_SS_FONT_IMPORT 							= "import org.apache.poi.ss.usermodel.Font;" + BREAK_LINE;
    public final String APACHE_POI_XSSF_XSSFSHEET_IMPORT 					= "import org.apache.poi.xssf.usermodel.XSSFSheet;"+ BREAK_LINE;
    public final String APACHE_POI_XSSF_XSSFWORKBOOK_IMPORT 				= "import org.apache.poi.xssf.usermodel.XSSFWorkbook;"+ BREAK_LINE;
    public final String APACHE_POI_XSSF_XSSFCELL_IMPORT 					= "import org.apache.poi.xssf.usermodel.XSSFCell;"+ BREAK_LINE;
    public final String APACHE_POI_XSSF_XSSFROW_IMPORT 						= "import org.apache.poi.xssf.usermodel.XSSFRow;"+ BREAK_LINE;

    public final String APACHE_HTTP_CLIENT_CLOSEADLE_HTTP_RESPONSE_IMPORT 	= "import org.apache.http.client.methods.CloseableHttpResponse;"+ BREAK_LINE;
    public final String APACHE_HTTP_CLIENT_METHODS_HTTP_GET_IMPORT 			= "import org.apache.http.client.methods.HttpGet;"+ BREAK_LINE;
    public final String APACHE_HTTP_CLIENT_METHODS_HTTP_POST_IMPORT			= "import org.apache.http.client.methods.HttpPost;"+ BREAK_LINE;
    public final String APACHE_HTTP_ENTITY_STRINGENTITY_IMPORT 				= "import org.apache.http.entity.StringEntity;" + BREAK_LINE;
    public final String APACHE_HTTP_IMPL_CLOSEADLE_HTTP_CLIENT_IMPORT 		= "import org.apache.http.impl.client.CloseableHttpClient;"+ BREAK_LINE;
    public final String APACHE_HTTP_IMPL_CLIENT_HTTP_CLIENTS_IMPORT 		= "import org.apache.http.impl.client.HttpClients;"+ BREAK_LINE;

    public final String JUNIT_TEST_IMPORT 									= "import org.junit.Test;"+ BREAK_LINE;
    public final String JUNIT_JUPITER_TEST_IMPORT 							= "import org.junit.jupiter.api.Test;"+ BREAK_LINE;

    public static final List<String> COMMON_METHODS = Arrays.asList("save", "findAllById", "findById", "delete", "deleteAll", "deleteById");
    public static final List<String> COMMON_IMPORTS = Arrays.asList( "Date", "List", "Map" );

    public static final String JUNIT_DEPENDENCY =
            "<dependency>\n" +
                    "            <groupId>org.junit.jupiter</groupId>\n" +
                    "            <artifactId>junit-jupiter</artifactId>\n" +
                    "            <version>5.8.2</version>\n" +
                    "            <scope>test</scope>\n" +
                    "        </dependency>";

    public static  final String MOCK_DEPENDENCY =
            "<dependency>\n" +
                    "            <groupId>org.mockito</groupId>\n" +
                    "            <artifactId>mockito-junit-jupiter</artifactId>\n" +
                    "            <version>3.12.4</version>\n" +
                    "            <scope>test</scope>\n" +
                    "        </dependency>";

    public static  final String MOCK_DEPENDENCY_core ="<dependency>\n" +
            "            <groupId>org.mockito</groupId>\n" +
            "            <artifactId>mockito-core</artifactId>\n" +
            "            <version>3.12.4</version>\n" +
            "            <scope>test</scope>\n" +
            "        </dependency>";


    public static  final String H2_DEPENDENCY_TEST ="<dependency>\n" +
            "    <groupId>com.h2database</groupId>\n" +
            "    <artifactId>h2</artifactId>\n" +
            "    <version>2.1.210</version>\n" +
            "    <scope>test</scope>\n" +
            "</dependency>";

    public static final String LOMBOK_DEPENDENCY = "<dependency>\n" +
            "    <groupId>org.projectlombok</groupId>\n" +
            "    <artifactId>lombok</artifactId>\n" +
            "    <version>1.18.34</version>\n" +
            "    <scope>provided</scope>\n" +
            "</dependency>";


    default String getBaseImport(Clase clase) {
        StringBuilder contex = new StringBuilder();
        contex.append("package ").append(clase.getPaquete()).append(";").append("\n");
        contex.append("import org.junit.jupiter.api.Test;").append("\n");
        contex.append("import org.junit.jupiter.api.BeforeEach;").append("\n");
        contex.append("import org.junit.jupiter.api.extension.ExtendWith;").append("\n");
        contex.append("import static org.assertj.core.api.Assertions.assertThat;").append("\n");
        contex.append("import org.assertj.core.api.Assertions;").append("\n");
    return contex.toString();
    }

    default String getMockImport() {
        StringBuilder contex = new StringBuilder();
        contex.append("import org.mockito.InjectMocks;").append("\n");
        contex.append("import org.mockito.Mock;").append("\n");
        contex.append("import org.mockito.InjectMocks;").append("\n");
        contex.append("import org.mockito.Mock;").append("\n");
        contex.append("import org.mockito.Mockito;").append("\n");
        contex.append("import org.mockito.MockitoAnnotations;").append("\n");
        contex.append("import org.mockito.junit.jupiter.MockitoExtension;").append("\n").append("\n");
        return contex.toString();
    }

}
