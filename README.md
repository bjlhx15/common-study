# common-base 
  基础项目
# 一、java-serializer 序列化项目
# 二、common-algorithm 通用算法
## 2.1、guava-consistenthash guava的一致性hash  
    示例：TestConsistentHash  
        一致性hash算法，顾名思义，尽量让hash结果能一致。可以应用在分布式、集群环境中的请求分发，  
        分布式缓存等场景之中。为了在集群节点的动态调整的时候，不会在rehash的时候导致大量的前后hash  
        运算结果不同问题，这就是要解决的hash一致性问题，而hash一致性算法要解决的就是这种动态调整hash  
        容器大小这后保证尽可能小的影响到hash结果。