def create_index(es_obj, index_name, settings):
    created = False
    try:
        if not es_obj.indeces.exists(index_name):
            es_obj.indices.create(index=index_name, ignore=400, body=settings)
            print("Created Index {}".format(index_name))
        created = True
    except Exception as ex:
        print(str(ex))
    finally:
        return created

