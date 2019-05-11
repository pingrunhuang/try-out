def search(es_obj, index_name, search):
    res = es_obj.search(index=index_name, body=search)