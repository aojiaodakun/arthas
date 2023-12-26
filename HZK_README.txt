arthas源码编译，版本：3.7.1
IDEA，Project Structure，SDK修改成8以上

手动添加D:\tool\java\jdk1.8.0_73\lib\tools.jar进IDEA依赖

***
输出class文件开关
GlobalOptions.isDump = true


一、sysprop
1、sysprop
2、sysprop | grep java

二、getstatic
1、getstatic demo.MathGame random

三、jad
jad demo.MathGame
classdump：com.taobao.arthas.core.util.InstrumentationUtils#retransformClasses
Cfr反编译：com.taobao.arthas.core.util.Decompiler#decompileWithMappings

四、stack
stack demo.MathGame primeFactors
com.taobao.arthas.core.command.monitor200.EnhancerCommand#enhance
Enhancer

五、thread



六、watch
watch demo.MathGame primeFactors

七、dashboard



【运行流程】
一、arthas-boot，mainClass=com.taobao.arthas.boot.Bootstrap
1、选择pid，执行java -jar arthas-core.jar，带参数
java -jar C:\Users\Administrator\.arthas\lib\3.7.1\arthas\arthas-core.jar -pid 13528 -core C:\Users\Administrator\.arthas\lib\3.7.1\arthas\arthas-core.jar -agent C:\Users\Administrator\.arthas\lib\3.7.1\arthas\arthas-agent.jar

二、arthas-core，mainClass=com.taobao.arthas.core.Arthas
1、解析入参，执行attach pid

三、arthas-agent，agentClass=com.taobao.arthas.agent334.AgentBootstrap
1、解析入参，另起线程执行com.taobao.arthas.core.server.ArthasBootstrap#getInstance，该构造方法会初始化，配置优化级（arthas.properties可反转到最高级），再启动nettyServer

【调试细节】
D:\tool\IntelliJ IDEA 2019.3.4\jbr\bin\java.exe -jar C:\Users\Administrator\.arthas\lib\3.7.1\arthas\arthas-core.jar
-pid 22032 
-core C:\Users\Administrator\.arthas\lib\3.7.1\arthas\arthas-core.jar 
-agent C:\Users\Administrator\.arthas\lib\3.7.1\arthas\arthas-agent.jar



java -jar 


java.exe -jar C:\Users\Administrator\.arthas\lib\3.7.1\arthas\arthas-core.jar -pid 29412 -core C:\Users\Administrator\.arthas\lib\3.7.1\arthas\arthas-core.jar -agent C:\Users\Administrator\.arthas\lib\3.7.1\arthas\arthas-agent.jar
java -jar C:\Users\Administrator\.arthas\lib\3.7.1\arthas\arthas-core.jar -pid 13528 -core C:\Users\Administrator\.arthas\lib\3.7.1\arthas\arthas-core.jar -agent C:\Users\Administrator\.arthas\lib\3.7.1\arthas\arthas-agent.jar



arthas指令白名单
com.taobao.arthas.core.command.BuiltinCommandPack#initCommands


java -jar 


-javaagent:D:\project\arthas-test\target\arthas-test-1.0-SNAPSHOT.jar

D:\tool\java\jdk1.8.0_73

vmtool --action getInstances --className kd.bos.bal.business.consumer.BalReCalConsumer --limit 10
vmtool --action getInstances --className kd.bos.bal.business.consumer.BalReCalConsumer --limit 10 --express 'instances[0].getClass().getProtectionDomain().getCodeSource()'
vmtool --action getInstances --className org.springframework.context.support.AbstractApplicationContext --limit 10
vmtool --action getInstances --className org.springframework.context.support.AbstractApplicationContext --express 'instances[0].getId()'
vmtool --action getInstances -c 14dad5dc --className org.springframework.context.support.AbstractApplicationContext --express 'instances[0].getBean("helloController")'
vmtool --action getInstances --className org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext --limit 10

vmtool --action getInstances --className org.springframework.context.support.AbstractApplicationContext --express 'instances[0].getBean("helloController")' 

vmtool --action interruptThread -t 10

