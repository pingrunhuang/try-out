from django.test import TestCase
from django.urls import resolve
from lists.views import home_page
from django.http import HttpRequest

# Create your tests here. Write this first!!!
class SmokeTest(TestCase):
    def test_fail(self):
        self.assertEqual(1+2,3)

class HomePageTest(TestCase):
    def test_root_url_resolvable(self):
        found = resolve("/")
        self.assertEqual(found.func, home_page)
    
    def test_home_page(self):
        request = HttpRequest()
        response = home_page(request)
        self.assertTrue(response.content.startswith(b"<html>"))
        self.assertContains(response, b"To-Do")
        self.assertTrue(response.content.endswith(b"</html>"))
