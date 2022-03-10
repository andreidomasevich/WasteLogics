import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class InvoicesPageTest extends BaseTest{

    private final String orderId = "146566"; // решил не заводить отдельный файл для хранения одного значения

    @BeforeClass
    @DataProvider(name = "invoices")
    public Object[][] openInvoicesPage(){
        String path = System.getProperty("user.dir");
        open("file://" + path + "/src/test/resources/Company invoices - Waste Logics.mhtml");
        List<WebElement> headerRows = getDriver().findElements(By.xpath("//*[@id=\"MyYardDisclosure9577009378082_frm\"]/thead[@class='tableFloatingHeaderOriginal']/tr"));

        Map<String, Integer> columnsIndexes = new HashMap<>();

        for (int i = 0; i < headerRows.size(); i++) {
            List<WebElement> columns =  getDriver().findElements(By.xpath("//*[@id=\"MyYardDisclosure9577009378082_frm\"]/thead[@class='tableFloatingHeaderOriginal']/tr[@class='gl-" + i + "']/th"));
            columns.forEach(Helper.withCounter((j, element) -> {
                columnsIndexes.put(element.getText(), j);
            }));
        }

        return new Object[][] {{columnsIndexes}};
    }

    @Test(dataProvider = "invoices")
    public void checkCompanyAndAddress(Map<String, Integer> columnsIndexes) {
        List<WebElement> gl0Columns = getDriver().findElements(By.xpath("(//td[text() = '" + orderId + "']/../../preceding-sibling::tbody[@class='gl-0 tgl eo ui-selectee'])[last()]/tr/td"));

        Assert.assertEquals(gl0Columns.get(columnsIndexes.get("Company")).getText(), "TEST CUSTOMER");
        Assert.assertEquals(gl0Columns.get(columnsIndexes.get("Invoice address")).getText(), "TEST ADDRESS, TEST TOWN, 111111");
    }

    @Test(dataProvider = "invoices")
    public void checkGradeAndWeight(Map<String, Integer> columnsIndexes) {
        List<WebElement> gl2Columns = getDriver().findElements(By.xpath("//td[text() = '" + orderId + "']/../../following-sibling::tbody[1]/tr[@class='gl-2']/td[not(@class='act ')]"));

        Assert.assertEquals(gl2Columns.get(columnsIndexes.get("Grade")).getText(), "Mixed Municipal Waste");
        Assert.assertEquals(gl2Columns.get(columnsIndexes.get("Weight")).getText(), "0.460 T");

    }

    @Test(dataProvider = "invoices")
    public void checkPrices(Map<String, Integer> columnsIndexes) {
        List<WebElement> gl3ColumnsFlatCharge = getFourthOrderLine(orderId, "Flat charge");
        List<WebElement> gl3ColumnsPerTonne = getFourthOrderLine(orderId, "per tonne");
        List<WebElement> gl3ColumnsItem = getFourthOrderLine(orderId, "Item");

        Assert.assertEquals(gl3ColumnsFlatCharge.get(columnsIndexes.get("Line total")).getText(), "£100.00");
        Assert.assertEquals(gl3ColumnsPerTonne.get(columnsIndexes.get("Line total")).getText(), "£4.60");
        Assert.assertEquals(gl3ColumnsItem.get(columnsIndexes.get("Line total")).getText(), "110.00");
    }

    private List<WebElement> getFourthOrderLine(String orderId, String priceEntity){
        return getDriver().findElements(By.xpath("(//td[text() = '"+ orderId + "']/../../following-sibling::tbody/tr[@class='gl-3']/td[text()='" + priceEntity +"'])[1]/../td[not(@class='act ')]"));
    }


}
