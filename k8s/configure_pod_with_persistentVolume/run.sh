mkdir $PWD/data
echo 'Hello from Kubernetes storage' > $PWD/data/index.html

# kubectl apply -f $PWD/pv-volume.yaml
# check pv 
# kubectl get pv task-pv-volume

# kubectl apply -f $PWD/pv-claim.yaml
# check pvc
# kubectl get pvc task-pv-claim

kubectl apply -f $PWD/pv-pod.yaml

# check the pod that use the pv
kubectl get pod task-pv-pod

# verification
# kubectl exec -it task-pv-pod -- /bin/bash
# root@task-pv-pod:/# apt-get update
# root@task-pv-pod:/# apt-get install curl
# root@task-pv-pod:/# curl localhost