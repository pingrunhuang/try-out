def store_record(es_obj, index_name, record):
    try:
        outcome = es_obj.index(index=index_name, doc_type='', body=record)
    except Exception as identifier:
        print("Error in indexing data")
        print(str(identifier))