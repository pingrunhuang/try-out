from django.test import TestCase
from django.urls import resolve
from lists.views import home_page

# Create your tests here. Write this first!!!
class SmokeTest(TestCase):
    def test_fail(self):
        self.assertEqual(1+1,3)

class HomePageTest(TestCase):
    def test_root_url_resolvable(self):
        found = resolve("/")
        self.assertEqual(found.func, home_page)
