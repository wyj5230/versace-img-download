import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

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
            rootTask(authToken, testList);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static void rootTask(String authToken, List<String> itemIdList) throws IOException
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

    private static void downloadImg(String imgUrl, String itemId) throws Exception
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
            itemIdlist.add(currentRow.getCell(0).getStringCellValue());
        }
        workbook.close();
        inputStream.close();
        return itemIdlist;
    }

}
