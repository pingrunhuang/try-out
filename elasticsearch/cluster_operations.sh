# check cluster status
curl -XGET http://localhost:9200/_cluster/health


# status: 
#   green: all the primary shard and replica shard are available
#   yellow: all the primary shard are available but not the replica shard
#   red: no available shard


# create an index with 3 primary and 1 replicas for each primary shards which result in 6 active shards, 5 primary shards by default
curl -XPUT http://localhost:9200/blogs -H "Content-Type:application/json" -d '{
    "settings": {
        "number_of_shards": 3,
        "number_of_replicas": 1
    }
}'

# RESULT: 
# {
# 	"cluster_name":"elasticsearch",
# 	"status":"yellow",
# 	"timed_out":false,
# 	"number_of_nodes":1,
# 	"number_of_data_nodes":1,
# 	"active_primary_shards":43, 
# 	"active_shards":43,
# 	"relocating_shards":0,
# 	"initializing_shards":0,
# 	"unassigned_shards":43, # replicas shards are not activated yet
# 	"delayed_unassigned_shards":0,
# 	"number_of_pending_tasks":0,
# 	"number_of_in_flight_fetch":0,
# 	"task_max_waiting_in_queue_millis":0,
# 	"active_shards_percent_as_number":50.0
# }


# 主分片的数量决定了index能存储的大小，从分片除了备份还决定了吞吐量(在提高节点数量的前提下）


curl -XPUT http://localhost:9200/blogs/_settings -H "Content-Type:application/json" -d '{
    "number_of_replicas": 2
}'