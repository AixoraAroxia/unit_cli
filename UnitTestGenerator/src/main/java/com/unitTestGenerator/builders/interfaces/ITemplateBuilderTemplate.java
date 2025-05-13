package com.unitTestGenerator.builders.interfaces;

public interface ITemplateBuilderTemplate {

    default String getConstructorsTemplate(){

     return " <div class=\"uml-methods\">\n" +
             "        <div class=\"uml-title-element\">@ConstructorsTitle@</div>\n" +
             "        <div class=\"uml-class\">\n" +
             "            <div class=\"uml-header\">@ConstructorsTitle1@</div>\n" +
             "            <div class=\"uml-attributes\">\n" +
             "                @listConstructors@\n" +
             "            </div>\n" +
             "        </div>\n" +
             "    </div>";
    }

    default String getImportTemplate(){
        return " <div class=\"uml-methods\">\n" +
                "        <div class=\"uml-title-element\">@ClassImportsTitle@</div>\n" +
                "        <div class=\"uml-class\">\n" +
                "                @ProjectImportsTemplate@\n" +
                "            \n" +
                "                @ExternalImportsTemplate@\n" +
                "            </div>\n" +
                "        </div>";

    }

    default String getImportTemplateProjectImports(){
            return "   <div class=\"uml-header\">@ProjectImportsTitle@</div>\n" +
                    "            <div class=\"uml-attributes\">\n" +
                    "                @ProjectImports@\n" +
                    "            </div>";
    }

    default String getImportTemplateExternalImports(){
        return "    <div class=\"uml-header\">@ExternalImportsTitle@</div>\n" +
                "            <div class=\"uml-attributes\">\n" +
                "                @ExternalImports@\n" +
                "            </div>";
    }

    default String getStructureInterfaceTemplate(){
        return  "        <div class=\"uml-class\">\n" +
                "            <div class=\"uml-header\">@Structure-ImplementsTitle@</div>\n" +
                "            @Structure-Implements@\n" +
                "        </div>\n";

    }

    default String getStructureExtendsTemplate(){
        return "\n" +
                "        <div class=\"uml-class\">\n" +
                "            <div class=\"uml-header\">@Structure-ExtendsTitle@</div>\n" +
                "            @Structure-Extends@\n" +
                "        </div>\n";

    }

    default String getAnotationsTemplate(){
        return "<div class=\"uml-methods\">\n" +
                "        <div class=\"uml-class\">\n" +
                "            <div class=\"uml-header\">@anotationsTitle@</div>\n" +
                "            <div class=\"uml-attributes\">\n" +
                "                @anotations@\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>";

    }

    default String getInterfaceTemplate(){
        return " <div class=\"uml-methods\">\n" +
                "        <div class=\"uml-title-element\">Interface: </div>\n" +
                "        \n" +
                "        @StructureInterfaceTemplate@\n" +
                "        \n" +
                "        @StructureExtendsTemplate@\n" +
                "    </div>";
    }


}
