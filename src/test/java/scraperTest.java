import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class scraperTest<t> {
    @Test
    public void site_header_is_on_home_page() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\tasos\\IdeaProjects\\Webdriver\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        int count2F=-2;
        int count3F=-2;
        String count3 = null;
        String count2 = null;
        String stringFinal;


        synchronized (driver) {
            ArrayList<String> verifiedList = new ArrayList<> ();
            //Firefox's geckodriver *requires* you to specify its location.
            String category = "Μοδίστρες και Επιδιορθώσεις Ενδυμάτων";
            String municipality = "ΔΗΜΟΣ ΠΕΡΑΜΑΤΟΣ ΑΤΤΙΚΗΣ";
            Actions action = new Actions(driver);
            driver.get("https://www.11888.gr/");

            driver.findElement(By.xpath("//INPUT[@placeholder=\"π.χ. Ηλεκτρολόγος, Μαλλιά\"]")).sendKeys(category);
            driver.wait(1000);
            driver.findElement(By.xpath(" /html/body/header/nav/div/div[2]/div[1]/div/div[1]/section/ul/li/div/span/strong")).click();
            driver.wait(1000);
            driver.findElement(By.id("suggester-location-input")).sendKeys(municipality);
            driver.wait(1000);
            driver.findElement(By.xpath("*//button[@class='g-search-bar__search-button']")).click();
            driver.wait(2500);
            int cnt=-1;
            String results =driver.findElement(By.xpath("/html/body/main/div/div[3]/div[1]/div/p")).getText();
            System.out.println(results);
//            String count1  = String.valueOf(results.charAt(0));
//            int count1F=Integer.parseInt(String.valueOf(count1));
//
//            try {
//                count2 = String.valueOf(results.charAt(1));
//                 count2F=Integer.parseInt(String.valueOf(count2));
//            }
//            catch (NumberFormatException e) {
//                  cnt=count1F;
//            }
//
//
//            if (count2F>-2){
//            try {
//            count3  = String.valueOf(results.charAt(2));
//            count3F=Integer.parseInt(String.valueOf(count3));}
//            catch (NumberFormatException e) {
//                cnt=count1F;
//            }
//                stringFinal=count1+count2;
//            }
//
//            if (count3F>-2){
//                stringFinal=count1+count2+count3;
//                cnt=Integer.parseInt(String.valueOf(stringFinal));
//            }
            int run=1;
            int i =1;
            for (int j = 2; j < 200+3; j++) {
                i=j-1;
                if (i==0){
                }
                else{
                driver.wait(100);
                String Title = driver.findElement(By.xpath("*//div[@id='yellow-pages-container']/div[@class='yellow-pages-results__container']/div[@data-count=" + i + "]/div[@class='listing-snippet__content']/div[@class='listing-snippet__details']/a[@class='listing-snippet__details__title']")).getText();
//                System.out.println(Title);
                driver.wait(100);
                String Address = driver.findElement(By.xpath("*//div[@id='yellow-pages-container']/div[@class='yellow-pages-results__container']/div[@data-count=" + i + "]/div[@class='listing-snippet__content']/div[@class='listing-snippet__details']/p[@class='listing-snippet__details__address']")).getText();
//                System.out.println(Address);
                driver.wait(100);
//                WebElement phone= driver.findElement(By.xpath("*//div[@id='yellow-pages-container']/div[@class='yellow-pages-results__container']/div[@data-count=" + i + "]/div[@class='listing-snippet__content']/div[@class='listing-snippet__footer']/div[@class='details-buttons']/div[@class='show-phone-list-button']"));
//                    driver.wait(100);
//                action.moveToElement(phone).perform();

                ((JavascriptExecutor) driver).executeScript("document.querySelector(\"#yellow-pages-container > div.yellow-pages-results__container > div[data-count='"+ i +"'] > div > div.listing-snippet__footer > div > div > div > div\").style='';");
                    driver.wait(100);
                WebElement Tel = driver.findElement(By.xpath("*//div[@id='yellow-pages-container']/div[@class='yellow-pages-results__container']/div[@data-count=" + i + "]/div[@class='listing-snippet__content']/div[@class='listing-snippet__footer']/div[@class='details-buttons']/div[@class='show-phone-list-button']/div/div/ul/li/span"));
                String TelText = Tel.getText();
//                System.out.println(TelText);
                String connector = "~";
                String finalString = Title + connector + Address + connector + TelText + connector + category + connector + municipality;
                System.out.println(finalString);
                verifiedList.add(finalString);
                writeVerifyDetails(verifiedList, category, municipality);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0,250)", "");
                driver.wait(100);
                }
                if (run==40){

                 WebElement changePage =   driver.findElement(By.xpath("*//i[@class='fa fa-angle-right']"));
                JavascriptExecutor executor = (JavascriptExecutor)driver;
                executor.executeScript("arguments[0].click();", changePage);
                    driver.wait(2500);
                    run=0;
                }
                run =run+1;
            }
        }
        }

    private void writeVerifyDetails(ArrayList<String> verifiedList, String category, String municipality){
        String filename = "C://Users//tasos//Downloads//ResultsScraper/" + category + "_" + municipality;
        try{
            FileWriter fileWriter =new FileWriter(filename,true);
            BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);
            for (String dataItem : verifiedList){
                bufferedWriter.write(dataItem + ";");
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
