import unittest
from selenium import webdriver

URL = "localhost:8080"

class VisitorTest(unittest.TestCase):
    def setUp(self):
        self.browser = webdriver.Chrome()
        # wait is not reliable
        self.browser.implicitly_wait(3)

    def tearDown(self):
        self.browser.quit()
    
    def test_visit_behaviour(self):
        self.browser.get(URL)
        # equal to: assert 'To-Do' in self.browser.title
        self.assertIn('To-Do', self.browser.title)
        self.fail('Finished')

if __name__ == "__main__":
    unittest.main(warnings='ignore')
