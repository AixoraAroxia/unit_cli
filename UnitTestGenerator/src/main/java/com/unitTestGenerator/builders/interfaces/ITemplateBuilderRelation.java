package com.unitTestGenerator.builders.interfaces;

public interface ITemplateBuilderRelation {


    default String relationCleanAll(String templete){
        templete = templete.replace("@ImplementTitle@", "");
        templete = templete.replace("@Implement@", "");
        templete = templete.replace("@ExtendsTitle@", "");
        templete = templete.replace("@Extends@", "");
        templete = templete.replace("@IocTitle@", "");
        templete = templete.replace("@Ioc@", "");
        templete = templete.replace("@StrongAssociationTitle@", "");
        templete = templete.replace("@StrongAssociation@", "");
        templete = templete.replace("@staticPatterBuildTitle@", "");
        templete = templete.replace("@staticPatterBuild@", "");
        return templete;
    }


    default String relationImport(String templete, String title, String content){
        if(title == null){ title = "";}
        if (content == null){ content = "";}
        String templeteInternal = getRelationImplement();
        templeteInternal = templeteInternal.replace("@ImplementTitle@", title);
        templeteInternal = templeteInternal.replace("@Implement@", content);
        templete = templete.replace("@ImplementTemplate@", templeteInternal);
        return templete;

    }

    default String relationExtends(String templete, String title, String content){
        if(title == null){ title = "";}
        if (content == null){ content = "";}
        String templeteInternal = getRelationExtends();
        templeteInternal = templeteInternal.replace("@ExtendsTitle@", title);
        templeteInternal = templeteInternal.replace("@Extends@", content);
        templete = templete.replace("@ExtendsTemplate@", templeteInternal);
        return templete;
    }


    default String relationIoc(String templete, String title, String content){
        if(title == null){ title = "";}
        if (content == null){ content = "";}
        String templeteInternal = getRelationIoc();
        templeteInternal = templeteInternal.replace("@IocTitle@", title);
        templeteInternal = templeteInternal.replace("@Ioc@", content);
        templete = templete.replace("@IocTemplate@", templeteInternal);
        return templete;
    }


    default String relationStrongAssociation(String templete, String title, String content){
        if(title == null){ title = "";}
        if (content == null){ content = "";}
        String templeteInternal = getRelationStrongAssociation();
        templeteInternal = templeteInternal.replace("@StrongAssociationTitle@", title);
        templeteInternal = templeteInternal.replace("@StrongAssociation@", content);
        templete = templete.replace("@StrongAssociationTemplate@", templeteInternal);
        return templete;
    }


    default String relationStaticPatterBuild(String templete, String title, String content){
        if(title == null){ title = "";}
        if (content == null){ content = "";}
        String templeteInternal = getRelationStaticPatterBuild();
        templeteInternal = templeteInternal.replace("@StaticPatterBuildTitle@", title);
        templeteInternal = templeteInternal.replace("@StaticPatterBuild@", content);
        templete = templete.replace("@StaticPatterBuildTemplate@", templeteInternal);
        return templete;
    }

    default String relationBaseCleanAll(String templete){
        templete = templete.replace("<!DOCTYPE html>", "");
        templete = templete.replace("<html lang=\"es\">", "");
        templete = templete.replace("<body>", "");
        templete = templete.replace("</body>", "");
        templete = templete.replace("</html>", "");
        return templete;
    }


    default String getRelationImplement(){
        return "    <div class=\"uml-class\">\n" +
                "        <div class=\"uml-header\">@ImplementTitle@</div>\n" +
                "        <div class=\"uml-attributes\">\n" +
                "            @Implement@\n" +
                "        </div>\n" +
                "    </div>";
    }

    default String getRelationExtends(){
        return " <div class=\"uml-class\">\n" +
                "        <div class=\"uml-header\">@ExtendsTitle@</div>\n" +
                "        <div class=\"uml-attributes\">\n" +
                "            @Extends@\n" +
                "        </div>\n" +
                "    </div>";
    }

    default String getRelationIoc(){
        return "   <div class=\"uml-class\">\n" +
                "        <div class=\"uml-header\">@IocTitle@</div>\n" +
                "        <div class=\"uml-attributes\">\n" +
                "            @Ioc@\n" +
                "        </div>\n" +
                "    </div>";
    }

    default String getRelationStrongAssociation(){
        return " <div class=\"uml-class\">\n" +
                "        <div class=\"uml-header\">@StrongAssociationTitle@</div>\n" +
                "        <div class=\"uml-attributes\">\n" +
                "            @StrongAssociation@\n" +
                "        </div>\n" +
                "    </div>";
    }

    default String getRelationStaticPatterBuild(){
        return "  <div class=\"uml-class\">\n" +
                "        <div class=\"uml-header\">@StaticPatterBuildTitle@</div>\n" +
                "        <div class=\"uml-attributes\">\n" +
                "            @StaticPatterBuild@\n" +
                "        </div>\n" +
                "    </div>";
    }






}
