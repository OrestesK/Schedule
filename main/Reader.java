import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

public class Reader {
	Window window;
	String user, pass;

	public Reader() throws IOException {

		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

		@SuppressWarnings("resource")
		WebClient webClient = new WebClient();
		HtmlPage page = webClient.getPage("https://k-5.schooltool.hflcampus.monroe.edu/schooltoolweb/");

		HtmlInput username = page.getElementByName("Template1$MenuList1$TextBoxUsername");
		HtmlInput password = page.getElementByName("Template1$MenuList1$TextBoxPassword");

		window = new Window("GetSchedule", this);
		while (true) {
			System.out.print("a");
			if (window.clicked == true) {
				System.out.println("hello3");
				user = window.getUsername();
				pass = window.getPassword();
				username.setValueAttribute(user);
				password.setValueAttribute(pass);
				window.clicked = false;
				break;
			}
		}

		HtmlSubmitInput loginButton = page.getElementByName("Template1$MenuList1$ButtonLogin");
		page = loginButton.click();
		webClient.waitForBackgroundJavaScript(1000);

		try {
			HtmlInput temp = page.getElementByName("Template1$MenuList1$TextBoxUsername");
			System.out.print("works");
		} catch (ElementNotFoundException a) {
			page = loginButton.click();
			webClient.waitForBackgroundJavaScript(1000);
			System.out.print("caught one");
		}
		try {
			HtmlSubmitInput continueButton = page.getElementByName("Template1$Control0$ButtonContinue");
			page = continueButton.click();
			webClient.waitForBackgroundJavaScript(1000);
		} catch (ElementNotFoundException a) {
			a.printStackTrace();
		}

		HtmlImage image = page.<HtmlImage>getFirstByXPath("//img[contains(@src, 'data:image/png;base64')]");
		page = (HtmlPage) image.click();
		webClient.waitForBackgroundJavaScript(1000);

		HtmlAnchor scheduleButton = page.getAnchorByHref(
				"javascript:__doPostBack('Template1$Control0$TabHeader2$Menutabs1$MenuTabSchedule','')");
		page = scheduleButton.click();

		/*
		 * trying to select the drop down menu HtmlSelect select = (HtmlSelect)
		 * page.getElementById("Template1$Control0$DropDownListCourses"); HtmlOption tab
		 * = select.getOptionByValue("-1"); select.setSelectedAttribute(tab, true);
		 */

		// saveFile(page);

		System.out.print(page.asXml());
	}

	public void saveFile(HtmlPage page) {

		try {
			String dir = System.getProperty("user.dir");
			FileOutputStream saveFile = new FileOutputStream(dir + "/schedule.txt");

			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			save.writeObject(page.asText());

			save.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(page.asXml());
	}

	public static void main(String[] args) throws Exception {
		new Reader();
	}
}
