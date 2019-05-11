from connect import connect_elasticsearch
import json, csv
import pypinyin

provinces = {}

def first_version():
    with open('poi_city_street_road_dimension.csv','r') as f:
        five_layer = csv.DictReader(f)
        for row in five_layer:
            province_name               = row['province_name']
            province_name_pinyin        = ''.join(pypinyin.lazy_pinyin(province_name, 2))
            # province_shortname          = row['province_name'][:-1]
            # province_shortname_pinyin   = ''.join(pypinyin.lazy_pinyin(province_name[:-1]))
            province_code               = row['province_code']

            city_name                   = row['city_name']
            city_name_pinyin            = ''.join(pypinyin.lazy_pinyin(city_name, 2)) if city_name else ''
            # city_shortname              = row['city_name'][:-1] if city_name else ''
            # city_shortname_pinyin       = ''.join(pypinyin.lazy_pinyin(city_shortname, 2)) if city_shortname else ''
            city_code                   = row['city_code']

            district_name               = row['district_name']
            district_name_pinyin        = ''.join(pypinyin.lazy_pinyin(district_name, 2)) if district_name else ''
            # district_shortname          = district_name[:-1] if district_name else ''
            # district_shortname_pinyin   = ''.join(pypinyin.lazy_pinyin(district_shortname, 2)) if district_shortname else ''
            district_code               = row['district_code']

            street_name                 = row['street_name']
            street_name_pinyin          = ''.join(pypinyin.lazy_pinyin(street_name, 2)) if street_name else ''
            # street_shortname            = street_name[:-1] if street_name else ''
            # street_shortname_pinyin     = ''.join(pypinyin.lazy_pinyin(street_shortname, 2)) if street_shortname else ''
            street_code                 = row['street_code']

            road_name                   = row['road_name']
            road_name_pinyin            = ''.join(pypinyin.lazy_pinyin(road_name, 2)) if road_name else ''
            # road_shortname              = road_name[:-1] if road_name else ''
            # road_shortname_pinyin       = ''.join(pypinyin.lazy_pinyin(road_shortname, 2)) if road_shortname else ''
            road_code                   = row['road_code']

            if not city_name or not district_name or not street_name or not road_name:
                continue

            if not provinces.get(province_name):
                provinces.update({
                    province_name:{
                        'province_name_pinyin': province_name_pinyin,
                        'province_code': province_code,
                        'cities':{}
                    }
                })
            if not provinces[province_name]['cities'].get(city_name):
                provinces[province_name]['cities'].update({
                    city_name:{
                        'city_name_pinyin': city_name_pinyin,
                        'city_code':city_code,
                        'districts':{}
                    }
                })
            if not provinces[province_name]['cities'][city_name]['districts'].get(district_name):
                provinces[province_name]['cities'][city_name]['districts'].update({
                    district_name:{
                        'district_name_pinyin': district_name_pinyin,
                        'district_code': district_code,
                        'streets':{}
                    }
                })
            if not provinces[province_name]['cities'][city_name]['districts'][district_name]['streets'].get(street_name):
                provinces[province_name]['cities'][city_name]['districts'][district_name]['streets'].update({
                    street_name:{
                        'street_name_pinyin': street_name_pinyin,
                        'street_code': street_code,
                        'roads':{}
                    }
                })
            if not provinces[province_name]['cities'][city_name]['districts'][district_name]['streets'][street_name]['roads'].get(road_name):
                provinces[province_name]['cities'][city_name]['districts'][district_name]['streets'][street_name]['roads'].update({
                    road_name:{
                        'road_name_pinyin':road_name_pinyin,
                        'road_code':road_code
                    }
                })

def second_version():
    with open('poi_city_street_road_dimension.csv','r') as f:
        five_layer = csv.DictReader(f)
        for row in five_layer:
            province_name               = row['province_name']
            province_name_pinyin        = ''.join(pypinyin.lazy_pinyin(province_name, 2))
            province_code               = row['province_code']

            city_name                   = row['city_name']
            city_name_pinyin            = ''.join(pypinyin.lazy_pinyin(city_name, 2)) if city_name else ''
            city_code                   = row['city_code']

            district_name               = row['district_name']
            district_name_pinyin        = ''.join(pypinyin.lazy_pinyin(district_name, 2)) if district_name else ''
            district_code               = row['district_code']

            street_name                 = row['street_name']
            street_name_pinyin          = ''.join(pypinyin.lazy_pinyin(street_name, 2)) if street_name else ''
            street_code                 = row['street_code']

            road_name                   = row['road_name']
            road_name_pinyin            = ''.join(pypinyin.lazy_pinyin(road_name, 2)) if road_name else ''
            road_code                   = row['road_code']

            if not city_name or not district_name or not street_name or not road_name:
                continue

            if not provinces.get(province_name):
                provinces.update({
                    province_name:{
                        'province_name_pinyin': province_name_pinyin,
                        'province_code': province_code,
                        'cities':[]
                    }
                })
            if not provinces[province_name]['cities'].get(city_name):
                provinces[province_name]['cities'].update({
                    city_name:{
                        'city_name_pinyin': city_name_pinyin,
                        'city_code':city_code,
                        'districts':[]
                    }
                })
            if not provinces[province_name]['cities'][city_name]['districts'].get(district_name):
                provinces[province_name]['cities'][city_name]['districts'].update({
                    district_name:{
                        'district_name_pinyin': district_name_pinyin,
                        'district_code': district_code,
                        'streets':[]
                    }
                })
            if not provinces[province_name]['cities'][city_name]['districts'][district_name]['streets'].get(street_name):
                provinces[province_name]['cities'][city_name]['districts'][district_name]['streets'].update({
                    street_name:{
                        'street_name_pinyin': street_name_pinyin,
                        'street_code': street_code,
                        'roads':[]
                    }
                })
            if not provinces[province_name]['cities'][city_name]['districts'][district_name]['streets'][street_name]['roads'].get(road_name):
                provinces[province_name]['cities'][city_name]['districts'][district_name]['streets'][street_name]['roads'].update({
                    road_name:{
                        'road_name_pinyin':road_name_pinyin,
                        'road_code':road_code
                    }
                })

def third_version():
    result = []
    with open('poi_city_street_road_dimension.csv','r') as f:
        five_layer = csv.DictReader(f)
        for row in five_layer:
            province_name               = row['province_name']
            province_name_pinyin        = ''.join(pypinyin.lazy_pinyin(province_name, 2))
            province_code               = row['province_code']
            city_name                   = row['city_name']
            city_name_pinyin            = ''.join(pypinyin.lazy_pinyin(city_name, 2)) if city_name else ''
            city_code                   = row['city_code']
            district_name               = row['district_name']
            district_name_pinyin        = ''.join(pypinyin.lazy_pinyin(district_name, 2)) if district_name else ''
            district_code               = row['district_code']
            street_name                 = row['street_name']
            street_name_pinyin          = ''.join(pypinyin.lazy_pinyin(street_name, 2)) if street_name else ''
            street_code                 = row['street_code']
            road_name                   = row['road_name']
            road_name_pinyin            = ''.join(pypinyin.lazy_pinyin(road_name, 2)) if road_name else ''
            road_code                   = row['road_code']
            if not city_name or not district_name or not street_name or not road_name:
                continue

            

            result.append(
                {
                    "province":{
                        "province_name": province_name,
                        "province_name_pinyin": province_name_pinyin,
                        "province_code": province_code,
                        "cities":{
                            "city_name": city_name,
                            "city_name_pinyin": city_name_pinyin,
                            "city_code": city_code,
                            "districts": 
                        }
                    }
                }
            )
        


# first_version()

es = connect_elasticsearch()

# import json
# import requests
# r = requests.get('http://localhost:9200') 
# i = 1
# while r.status_code == 200:
#     r = requests.get('http://swapi.co/api/people/'+ str(i))
#     data = json.loads(r.content)
#     print(data)
#     es.index(index='star_war', doc_type='people', id=i, body=data)
#     i=i+1
 
# print(i)

res = es.get(index='star_war', doc_type='people', id=1)
print(res)

# es.indices.refresh(index='five_layer')
# res = es.search(index='five_layer', body={'query': {'match_all':{}}})

# print('Got %d Hits: ' % res['hits']['total'])
# for hit in res['hits']['hits']:
#     print(hit['_source'])