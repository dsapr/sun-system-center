service 工程模板执行
```
mvn archetype:generate
 -DgroupId=com.dsapr 
 -DartifactId=sun-test 
 -Dversion=1.0.0-SNAPSHOT 
 -Dpackage=com.dsapr.test
 -DappName=test-demo
 
 -DarchetypeArtifactId=sun-service-archetype 
 -DarchetypeGroupId=com.dsapr 
 -DarchetypeVersion=1.0-SNAPSHOT
```
```

mvn archetype:generate -DgroupId=com.dsapr -DartifactId=sun-test -Dversion=1.0.0-SNAPSHOT -Dpackage=com.dsapr.test -DappName=test-demo -DarchetypeArtifactId=sun-service-archetype -DarchetypeGroupId=com.dsapr -DarchetypeVersion=1.0-SNAPSHOT
```