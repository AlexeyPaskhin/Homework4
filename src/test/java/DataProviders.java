import org.testng.annotations.DataProvider;

/**
 * Created by Лешка on 22.12.2016.
 */
public class DataProviders {
    @DataProvider(name = "point4")
    public static Object[] [] point4() {
        return new Object[][] {
                {"I love QATestLab"},
                {"eeeee"},
                {"roock"},
                {"вареники"}
    };

    }
}
