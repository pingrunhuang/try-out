import logging
from elasticsearch import Elasticsearch

HOST_CONFIG = [{'host':'localhost', 'port':9200}]

def connect_elasticsearch():
    _es = Elasticsearch(HOST_CONFIG)
    if _es.ping():
        print('Yay connect')
    else:
        print('Not connect')
    return _es

if __name__ == '__main__':
    logging.basicConfig(level=logging.ERROR)
    