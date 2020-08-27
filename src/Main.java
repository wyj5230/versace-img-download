import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class Main
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入token:");
        String authToken = sc.next();

        String filePath = "./versace/versace.xls";
        try
        {
            List<String> testList = getItemIdExcel(filePath);
            getLargeImages(authToken, testList);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
//        String cookie = "ehHduD_II_jXTW3s76dkFuovvxoPkto5A8ykDDCJUoTiDSxbrA5YSQ-0K0kKcpsrff8ljQzolDihpHknB7SSiS2tj5LaVAzIHaF8CqwQC56nOtZ7sbx7bzjimy-kEZKOStoBQIuiNRkq4iEwZsbiSuA7zUna-Ha1d9gsKxJ7VLjyCV8ioyJd83OPJadhAQi27UDMgETcGSuquxfO84WiEZLMK8eRivw4CYog6s11DPsrirxprO38pqczOoYibfadiEDLhPid1NBWR7uuKbNlxGzuhvgjGxND3846OCQAixZXAv38hkLmAtMqo8VD7a83AhZSgMZa7hhNqZkgpX03L7GY5UmkXfuZJY2lptAw1oOIOKIRQFt2ez9Ugfx0ALbFDb3K4183qu_AFx0zE0FMVeFNiRJAu36PPzVdukXwHLHb3wQ_g0PIjC9mLYjH65JMFivp_-X9pwKgXVjj5x2qz9NapGJ1xbyTEAUPug4goq98agZ5K-nqXzw65IJEfHIn8CIHeFvmgxxJuxeb1l2sxOeSY9EslauL1YHvkxVAMLpVI1ZmVxWKVyRdQIUpXjbhNSurm1gcGPKXXuyQrIRIPWLcNPu5Qu-T6Xndh_l0OgmBHzZQZet6Y4pMgcO6HJSqhDS4wH6jqZ7e35aZEXTe8ToMg5i4J8mzetWwvZTfkKKfIKAMLRWw4KOO4D_bxiNHcFCcuMUqh9Iq0sOsVWqMlFQ9f72GC14SH4r2nibugbVA4CEtn31cBpGk07Bng60MRoiyhBAeo7kYg7NMWJ5y7Ro_PZrkevbrOM6vFLQCpNIf0pHQ-1VAC-2f7N7RAozp";
//        String url = "https://versaceshowroom.chalco.net/VirtualShowroom/Dam/Navigate?GroupKey=vY%2B9DoJAEIRfhUy9hRwQ5Fo7S2OkMBQXchoSgeR%2BaAjv7mavsLBRC5vdZDLz7eyKO%2FR1xQSNi3Xe9DY7WjP57DDHEJ0FoYduyoJwE%2BcCnRMWxQGItiKwoWHNQ6uNMA5sKfM9wYvSbZQOtPNopgSsdgx5COpXLEmo%2BiBU1M1bl9O5TU1UpaQJz4FXcNH%2BuRN9cal%2BPdJtTw%3D%3D&Groups=products-grouping&Levels=2&SelectionID=0&QueryHash=1111111&WorkgroupId=99&Section=product-catalogue&QuerySource=Undefined&DownloadPreview=False&Page=3";
//        String header = "__RequestVerificationToken_L1ZpcnR1YWxTaG93cm9vbQ2=mIH5MienxmXq2l3EYimF1TzImiOYZKptPF0s_dYYtLOoyV1Vps-1sk85BkFWwG2HlF91z98jJo2wUezSfj3TS3HgSf_lutfdVu_At5E_ynY1; Culture=en; ASP.NET_SessionId=z1acig2p5q01mfdcfwrdgdfq; CloudFront-Key-Pair-Id=APKAIOOVA6CIMD6MQJMA; CloudFront-Policy=eyJTdGF0ZW1lbnQiOiBbeyJSZXNvdXJjZSI6IioiLCJDb25kaXRpb24iOnsiRGF0ZUxlc3NUaGFuIjp7IkFXUzpFcG9jaFRpbWUiOjE1OTgxNjMzODl9fX1dfQ__; CloudFront-Signature=JlyvKWzbnj4vdzjoLU5DxupcZXq8kD-3moTexCOs6zld3aAzwH8yDIoLwIE48xjyLuwhgpkSkQvtxEYY-5HHNI6UDlpyARNT1FXhF~oZkGi1q4HjoYDwKBL474zFSVGUXCXcU0GeBwFnuSj1rk1RLQUTFS7pV1ToJA~QeV1SldWK~QI5qswlrguofsnyktDmLRWIUAWbuJ0iKKaaDaSYjgMu25UhesHvGmjqsK-YGwluNe6sLS-SqM9toLaoW0AUQLwYKDYV1IirXmwsbJKlzrwYUxieWHHt0E1QKVMCbm8~9HxPnvjlGxzHcl1I26VfRKMRLsDH9wuvcfk4QG9FjQ__; VirtualShowroom=ehHduD_II_jXTW3s76dkFuovvxoPkto5A8ykDDCJUoTiDSxbrA5YSQ-0K0kKcpsrff8ljQzolDihpHknB7SSiS2tj5LaVAzIHaF8CqwQC56nOtZ7sbx7bzjimy-kEZKOStoBQIuiNRkq4iEwZsbiSuA7zUna-Ha1d9gsKxJ7VLjyCV8ioyJd83OPJadhAQi27UDMgETcGSuquxfO84WiEZLMK8eRivw4CYog6s11DPsrirxprO38pqczOoYibfadiEDLhPid1NBWR7uuKbNlxGzuhvgjGxND3846OCQAixZXAv38hkLmAtMqo8VD7a83AhZSgMZa7hhNqZkgpX03L7GY5UmkXfuZJY2lptAw1oOIOKIRQFt2ez9Ugfx0ALbFDb3K4183qu_AFx0zE0FMVeFNiRJAu36PPzVdukXwHLHb3wQ_g0PIjC9mLYjH65JMFivp_-X9pwKgXVjj5x2qz9NapGJ1xbyTEAUPug4goq98agZ5K-nqXzw65IJEfHIn8CIHeFvmgxxJuxeb1l2sxOeSY9EslauL1YHvkxVAMLpVI1ZmVxWKVyRdQIUpXjbhNSurm1gcGPKXXuyQrIRIPWLcNPu5Qu-T6Xndh_l0OgmBHzZQZet6Y4pMgcO6HJSqhDS4wH6jqZ7e35aZEXTe8ToMg5i4J8mzetWwvZTfkKKfIKAMLRWw4KOO4D_bxiNHcFCcuMUqh9Iq0sOsVWqMlFQ9f72GC14SH4r2nibugbVA4CEtn31cBpGk07Bng60MRoiyhBAeo7kYg7NMWJ5y7Ro_PZrkevbrOM6vFLQCpNIf0pHQ-1VAC-2f7N7RAozp";
//        Document doc  = Jsoup.connect(url).header("cookie",header).cookie("VirtualShowroom",cookie).timeout(60000).validateTLSCertificates(false).get();
//        Elements elements = doc.select("div#asset-container div div a[href$='=1']");
//        List<String> errorProducts = new ArrayList<>();
//        for (Element e : elements){
//            String result = downloadLargeImage(e,cookie,header);
//            if (result != null){
//                errorProducts.add(result);
//            }
//        }
//        System.out.println("Job Finished, failed products: ");
//        for (String productId : errorProducts){
//            System.out.println(productId);
//        }
    }

    private static void downloadImage(Element element, String cookie, String header) throws IOException
    {
        String titleWithSpace = element.attr("title");
        String title = titleWithSpace.replace(" ","");
        String productId = element.attr("data-assetid");
        String imgUrl = "https://versaceshowroom.chalco.net/VirtualShowroom/damimage/auth/product-main-image/"+title+"/medium?productId="+productId+"&___global_cache=23";
        System.out.println(title);
        System.out.println(productId);
        System.out.println(imgUrl);
        Connection.Response resultImageResponse = Jsoup.connect(imgUrl).header("cookie",header).cookie("VirtualShowroom",cookie)
                .ignoreContentType(true).execute();
        FileOutputStream out = (new FileOutputStream(new java.io.File("./versace/" +  titleWithSpace + ".jpg")));
        out.write(resultImageResponse.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
        out.close();

    }

    private static String downloadLargeImage(Element element, String cookie, String header) throws IOException
    {
        String titleWithSpace = element.attr("title");
        String detailLink = element.attr("href");
        System.out.println(detailLink);
        Document doc  = Jsoup.connect("https://versaceshowroom.chalco.net/"+detailLink).header("cookie",header).cookie("VirtualShowroom",cookie).timeout(60000).validateTLSCertificates(false).get();
        Elements elements = doc.select("a[data-toggle='lightbox']");
        String imageLink = elements.get(0).attr("href");
        if (imageLink.startsWith("java")) {
            return titleWithSpace;
        }
        System.out.println(imageLink);
        Connection.Response resultImageResponse = Jsoup.connect(imageLink).header("cookie",header).cookie("VirtualShowroom",cookie)
                .ignoreContentType(true).execute();
        FileOutputStream out = (new FileOutputStream(new java.io.File("./versace/" +  titleWithSpace + ".jpg")));
        out.write(resultImageResponse.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
        out.close();
        return null;
    }


    //http://dashboard-versace.neticon.it:8080/items
    private static void getImages(String authToken, List<String> itemIdList) throws IOException
    {
        CookieStore cookieStore = new BasicCookieStore();
        HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        String searchUrl = "http://repo-versace.neticon.it:8080/rest/items/0/10/";
        for (String itemId : itemIdList)
        {
            try
            {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            System.out.println("Thead started");
                            String imgUrl = retrieveImgUrl(client, searchUrl, authToken, itemId);
                            downloadImg(imgUrl, itemId);
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }).start();
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        System.out.println();
        System.out.println("Task complete, " + itemIdList.size() + " images downloaded.");
    }

    //http://dashboard-versace.neticon.it:8080/items
    private static void getLargeImages(String authToken, List<String> itemIdList) throws IOException
    {
        CookieStore cookieStore = new BasicCookieStore();
        HttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        String searchUrl = "http://repo-versace.neticon.it:8080/rest/items/0/10/";
        List<String> failedIds = new ArrayList<>();
        for (String itemId : itemIdList)
        {
            try
            {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            System.out.println("Thead started");
                            boolean result = retrieveLargeImgUrl(client, searchUrl, authToken, itemId);
                            if (!result) {
                                failedIds.add(itemId);
                            }
                        } catch (Exception ex) {
                            failedIds.add(itemId);
                            System.out.println(ex);
                        }
                    }
                }).start();
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        System.out.println();
        System.out.println("Task started, " + itemIdList.size() + " items downloaded. However, each item may contain multiple images.");
    }

    private static String retrieveImgUrl(HttpClient client, String searchUrl, String authToken, String itemId) throws
            IOException
    {
        HttpGet searchItem = new HttpGet(searchUrl + itemId);
        searchItem.setHeader("x-auth-token", authToken);
        HttpResponse searchResponse = client.execute(searchItem);
        HttpEntity entity = searchResponse.getEntity();
        String jsonResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        JSONObject item = new JSONObject(jsonResponse);
        JSONObject imgObject = item.getJSONArray("items")
                .getJSONObject(0).getJSONArray("shotCollection").getJSONObject(0);
        boolean isSwatch = imgObject.getBoolean("swatch");
        System.out.println(isSwatch);
        if (isSwatch) {
            imgObject = item.getJSONArray("items")
                    .getJSONObject(0).getJSONArray("shotCollection").getJSONObject(1);
            isSwatch = imgObject.getBoolean("swatch");
            if (isSwatch) {
                imgObject = item.getJSONArray("items")
                        .getJSONObject(0).getJSONArray("shotCollection").getJSONObject(2);
            }
        }
        String imgUrl = imgObject.getJSONArray("shotsVersionList").getJSONObject(0).getString("thumbnail");
        System.out.println("Get image url successfully for item: " + itemId);
        return imgUrl;
    }

    private static boolean retrieveLargeImgUrl(HttpClient client, String searchUrl, String authToken, String itemId) throws
            IOException
    {
        HttpGet searchItem = new HttpGet(searchUrl + itemId);
        searchItem.setHeader("x-auth-token", authToken);
        HttpResponse searchResponse = client.execute(searchItem);
        HttpEntity entity = searchResponse.getEntity();
        String jsonResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        JSONObject item = new JSONObject(jsonResponse);
        JSONArray thumbnails = item.getJSONArray("items")
                .getJSONObject(0).getJSONArray("shotCollection");
        boolean result = true;
        for (int i = 0; i< thumbnails.length(); i++ ){
            JSONObject thumbnail = thumbnails.getJSONObject(i);
            String bigImg = thumbnail.getJSONArray("shotsVersionList").getJSONObject(0).getString("thumbnail");
            if (!thumbnail.getBoolean("swatch")) {
                bigImg = thumbnail.getJSONArray("shotsVersionList").getJSONObject(0).getString("big");
                bigImg = "http://repo-versace.neticon.it:8080/" + bigImg;
            }
            try {
                downloadImg(bigImg,itemId + "_" + i );
            } catch (Exception e) {
                result = false;
            }
        }
        return result;
    }


    private static void downloadImg(String imgUrl, String itemId)
    {
        try (InputStream in = new URL(imgUrl).openStream())
        {
            Files.copy(in, Paths.get("./versace/" + itemId + ".jpg"), REPLACE_EXISTING);
            System.out.println("Download img successfully for item: " + itemId);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static List<String> getItemIdExcel(String path) throws Exception
    {
        FileInputStream inputStream = new FileInputStream(new File(path));
        List<String> itemIdlist = new ArrayList<>();
        Workbook workbook = new HSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        int total = firstSheet.getLastRowNum();
        for (int i = 0; i <= total; i++)
        {
            Row currentRow = firstSheet.getRow(i);
            if (currentRow == null || currentRow.getCell(0) == null) {
                break;
            } else {
                itemIdlist.add(currentRow.getCell(0).getStringCellValue());
            }
        }
        workbook.close();
        inputStream.close();
        return itemIdlist;
    }

}
