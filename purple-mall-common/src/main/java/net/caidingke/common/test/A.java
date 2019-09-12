package net.caidingke.common.test;

/**
 * @author bowen
 * @date 2019-08-06 15:10
 */
public enum A {
    B(){
        @Override
        String a(String a) {
            return null;
        }


        //        @Override
//        String a(String a) {
//            return a.concat("a");
//        }
    },
    C(){
        @Override
       String a(String a) {
            return a.concat("c");
        }
    };
    abstract String a(String a);


}
