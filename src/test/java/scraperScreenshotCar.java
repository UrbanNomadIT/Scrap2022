import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


class scraperScreenshotCar<t> {
    @Test
    public void screenshot_to_get_generic() throws InterruptedException, IOException, AWTException {

        int count2F = -2;
        int count3F = -2;
        String count3 = null;
        String count2 = null;
        String stringFinal;

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\tasos\\IdeaProjects\\Webdriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
            ArrayList<String> verifiedList = new ArrayList<>();

            String media = "XE";
            String date = "13Dec2021";
            String category = "EPIPLA";
            String filePath="C:\\Users\\tasos\\Downloads\\scrapperScreenshot\\";
            ArrayList<String> refList = getReferenceList("", filePath, date , media, category);
            String lineText=null;
            Actions action = new Actions(driver);
            int count=0;
            driver.wait(2000);
            for (String artcleRef : refList) {
                count = count+1;
                driver.get(artcleRef);
                driver.wait(500);
                synchronized (driver) {
                // Για να αποδεχτεί τα κουκις την πρώτη φορά που θα ανοιίξει το marketplace
                if(category == "Marketplace" && count==1) {
                    driver.findElement(By.xpath("//SPAN[@class='a8c37x1j ni8dbmo4 stjgntxs l9j0dhe7 ltmttdrg g0qnabr5'][text()='Allow All Cookies']")).click();

                    driver.wait(400);
                }
                String filename = filePath +"//" + date + "//" + media + "//" +  category + "//"+ date + "_" + media + "_" +  category+ "_"+count+".jpg";

                String capture = filename;

                byte screenshot[] = (byte[]) ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                FileOutputStream fos = new FileOutputStream(capture);
                fos.write(screenshot);
                driver.wait(500);
            }
}
    }

    public ArrayList<String> getReferenceList(String listName, String filePath, String date, String media, String category) {
        ArrayList<String> lstRefs = new ArrayList<String>();

        String filePath1 = filePath+"//" + date + "//" + media + "//" +  category + "//http.txt";

        try {
            BufferedReader lineReader=new BufferedReader(new FileReader(filePath1));
            String lineText=null;

            while((lineText=lineReader.readLine())!=null)
            {
                lstRefs.add(lineText);
            }
            lineReader.close();

        } catch (IOException ex) {

        }
        return lstRefs;
    }

}
