MASTER_IP='http://localhost'
PORT='9200'

echo Pattern
echo $MASTER_IP:$PORT/Index/Type/Field

# put a new document with auto incr id
curl -XPUT http://localhost:9200/megacorp/employee/1 -H "Content-Type:application/json" -d '
{
	"first_name": "John",
	"last_name": "Smith",
	"age": 25,
	"about": "I love to go rock climbing",
	"interests": ["sports", "music"]
}'

# post a new document without specify incr id
curl -XPOST http://localhost:9200/megacorp/employee -H "Content-Type:application/json" -d '
{
	"first_name": "John",
	"last_name": "Smith",
	"age": 25,
	"about": "I love to go rock climbing",
	"interests": ["sports", "music"]
}'



curl -XPUT http://localhost:9200/megacorp/employee/2 -H "Content-Type:application/json" -d '
{
	"first_name": "Jane",
	"last_name": "Smith",
	"age": 32,
	"about": "I like collect rock albums",
	"interests": ["music"]
}'

curl -XPUT http://localhost:9200/megacorp/employee/3 -H "Content-Type:application/json" -d '
{
	"first_name": "Douglas",
	"last_name": "Fir",
	"age": 35,
	"about": "I like to build cabinets",
	"interests": ["forestry"]
}'

# retrieve data
curl -XGET $MASTER_IP:$PORT/megacorp/employee/1 -H "Content-Type:application/json" 


# retrieve first 10 by default
curl -XGET $MASTER_IP:$PORT/megacorp/employee/_search -H "Content-Type:application/json"


# search for entries that have the family name smith
curl -XGET http://localhost:9200/megacorp/employee/_search?q=last_name:Smith -H "Content-Type:application/json"


# use query DSL to achieve the same like operation
curl -XGET http://localhost:9200/megacorp/employee/_search -H "Content-Type:application/json" -d '{
    "query": {
        "match": {
            "last_name": "Smith"
        }
    }
}'

# add more constraint with DSL: search for age larger then 30 years old
curl -XGET http://localhost:9200/megacorp/employee/_search -H "Content-Type:application/json" -d '
{
    "query": {
        "filtered": {
            "filter": {
                "range": {
                    "age": {"gt": 30}
                }
            },
            "query": {
                "match": {
                    "last_name": "Smith"
                }
            }
        }
    }
}'

# fuzzy search for the whole page 
curl -XGET http://localhost:9200/megacorp/employee/_search -H "Content-Type:application/json" -d '
{
    "query": {
        "match": {
            "about": "rock climbing"
        }
    }
}'

# search for whole page that match exactly the same
curl -XGET http://localhost:9200/megacorp/employee/_search -H "Content-Type:application/json" -d '
{
    "query": {
        "match_phrase": {
            "about": "rock climbing"
        }
    }
}'

# highlight saerch result 
curl -XGET http://localhost:9200/megacorp/employee/_search -H "Content-Type:application/json" -d '
{
    "query": {
        "match_phrase": {
            "about": "rock climbing"
        }
    },
    "highlight": {
        "fields": {
            "about": {}
        }
    }
}'

# aggregations (group by)


# find out what popular interests that people like
curl -XGET http://localhost:9200/megacorp/employee/_search -H "Content-Type:application/json" -d '
{
    "aggs": {
        "all_interests": {
            "terms": {"field": "interests.keyword"}
        }
    }
}'

# find out average age
curl -XGET http://localhost:9200/megacorp/employee/_search -H "Content-Type:application/json" -d '
{
    "aggs": {
        "average_age": {  
            "avg": {"field": "age"}        
        }
    }
}'

# nested 
curl -XGET http://localhost:9200/megacorp/employee/_search -H "Content-Type:application/json" -d '
{
    "query": {
        "match": {
            "last_name": "smith"
        }
    },
    "aggs": {
        "all_interests": {
            "terms": {
                "field": "interests.keyword"
            }
        }
    }
}'





# insert data with or without id 


curl -XPOST http://localhost:9200/website/blog/ -H "Content-Type:application/json" -d '
    {
        "title": "hello world",
        "author": "frank",
        "date": "20140101"
    }
'
curl -XPUT http://localhost:9200/website/blog/1 -H "Content-Type:application/json" -d '
    {
        "title": "hello world",
        "author": "frank",
        "date": "20140101"
    }
'


# partial get document
curl -XGET 'http://localhost:9200/website/blog/_ojyz2oBprOPpPbSeOVX/_source?pretty' -H "Content-Type:application/json" 
curl -XGET 'http://localhost:9200/website/blog/_ojyz2oBprOPpPbSeOVX?_source=title,author&&pretty' -H "Content-Type:application/json" 


# check if a document exists: /index/_doc/id
curl -I http://localhost:9200/website/_doc/_ojyz2oBprOPpPbSeOVX


# partial update document
curl -XPOST http://localhost:9200/website/blog/1/_update -H "Content-Type:application/json" -d '
    {
        "doc": {
            "tags": ["testing"],
            "views": 0
        }
    }
'

# multiple get 
curl -XGET http://localhost:9200/_mget -d '{
    "docs": [
        {
            "_index":"website",
            "_type":"blog",
            "_id":2
        },
        {
            "_index":"website",
            "_type":"pageviews",
            "_id":1,
            "_source":"views"
        }
    ]
}'

# bulk create, index, update, delete
curl -XPOST http://localhost:9200/_bulk -H "Content-Type:application/json" -d '
    {"create":{"_index":"website", "_type":"blog", "_id":"123"}}\n
    {"delete":{"_index":"website", "_type":"blog", "_id":"123"}}\n'







