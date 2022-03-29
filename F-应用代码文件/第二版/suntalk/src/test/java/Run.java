import site.hnuster.utils.Encoder;
import site.hnuster.utils.SimpleRequest;

import java.util.ArrayList;
import java.util.List;

public class Run {
    public static void main(String[] args) throws Exception {
        List<String> paraList = new ArrayList<>();
        //alert(generateAur("101","123","asd","qqq"))
        //6e6c6fee5712ce8a88888887a8ed3174d4053666
        paraList.add("101");
        paraList.add("123");
        paraList.add("asd");
        paraList.add("qqq");
        String aur = Encoder.sha1(paraList);
        System.out.println(aur);
        //System.out.println(new Login().setId("100001").setTimestamp("123456").setNonce("illasdh").setAur(aur).execute());

        List<String> list = new ArrayList<>();
        list.add("25@2");
        System.out.println(ckType(list));
    }
    public static boolean ckType(List<?> ckList) {
        boolean ok = true;
        for (Object s:ckList){
            String str = (String) s;
            if (!str.contains("@")||str.indexOf("@")==str.length()-1){
                ok = false;
                break;
            }
            System.out.println(str.substring(str.indexOf("@")+1));
            try{
                if (SimpleRequest.get(
                        "https://"+str.substring(str.indexOf("@")+1)
                        ,""
                ).getStatusLine().getStatusCode()!=200){
                    ok = false;
                    break;
                }
            }catch (Exception e){
                ok = false;
            }
        }
        return ok;
    }
}
