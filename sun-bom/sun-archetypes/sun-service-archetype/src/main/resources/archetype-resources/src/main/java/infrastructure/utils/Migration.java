#set( $symbol_pound = '#' )
        #set( $symbol_dollar = '$' )
        #set( $symbol_escape = '\' )
        package ${package}.infrastructure.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Migration {

    public static void main(String[] args) {
        String fileName = "create_user_table";

        String path = "src/main/resources/db/migration/";

        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
        String format = sdf.format(new Date());

        fileName = 'V' + format + "__" + fileName + ".sql";

        System.out.println(fileName);
        File file = new File(path + fileName);

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
