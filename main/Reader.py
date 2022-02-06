import datetime
from time import sleep
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.common.keys import Keys


def main():
    chropt = webdriver.ChromeOptions()
    chropt.add_argument('--disable-extensions')
    chropt.add_argument("window-size=1366,625")
    chropt.add_argument("--headless")
    driver = webdriver.Chrome(options=chropt)
    driver.get("https://k-5.schooltool.hflcampus.monroe.edu/schooltoolweb/")

    WebDriverWait(driver, 5).until(
        EC.presence_of_element_located((By.XPATH, '//*[@id="Template1_MenuList1_TextBoxUsername"]')))

    driver.find_element_by_xpath(
        '//*[@id="Template1_MenuList1_TextBoxUsername"]').send_keys('Username') //change to username
    password = driver.find_element_by_xpath(
        '//*[@id="Template1_MenuList1_TextBoxPassword"]').send_keys('Password') //change to password
    driver.find_element_by_xpath(
        '//*[@id="Template1_MenuList1_ButtonLogin"]').click()

    WebDriverWait(driver, 5).until(EC.element_to_be_clickable(
        (By.XPATH, "//img[contains(@src, 'data:image/png;base64')]")))

    my_face = driver.find_element_by_xpath(
        "//img[contains(@src, 'data:image/png;base64')]").click()

    WebDriverWait(driver, 5).until(EC.element_to_be_clickable(
        (By.XPATH, "//a[text()='Schedule']")))

    driver.find_element_by_xpath("//a[text()='Schedule']").click()

    # there are 10 rows
    # row_1 = '//*[@id="Template1_Panel_Content"]/table[4]/tbody/tr[1]/td/table/tbody/tr[1]'
    # there are 5 columns
    # column_1 = '//*[@id="Template1_Panel_Content"]/table[4]/tbody/tr[1]/td/table/tbody/tr[1]/td[1]'

    today = datetime.datetime.today().weekday()
    day = None
    contents = []

    if today < 5:
        day = today + 1

    for x in range(1, 11):
        contents.append(driver.find_element_by_xpath(
            '//*[@id="Template1_Panel_Content"]/table[4]/tbody/tr[1]/td/table/tbody/tr[{}]/td[{}]'.format(x, day)).text)
        contents.append("")

    return contents


if __name__ == "__main__":
    for elem in main():
        print(elem)
