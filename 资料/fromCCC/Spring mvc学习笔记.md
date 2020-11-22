# Spring mvc学习笔记

分为解决什么问题、基本概念、怎么解决问题以及在前后端分离项目中的应用（结合小组的项目）。

## 解决什么问题

- 业务逻辑、界面、用户交互等经常因需求而改变，每次改变需求都带来大量的重构工作。
- 开发往往因追求效率而需要前后端同时进行开发，因此需要视图层和业务层的耦合性尽可能低，避免频繁出现诸如前端开发进度因后端的开发进度而阻塞这种情况。
- 很多业务在多个视图中会重用，希望尽可能提高代码复用程度。

## 基本概念

### mvc概念

- 模型（model）：负责存储系统的中心数据。
- 视图（view）：将信息显示给用户。
- 控制器（controller）：处理用户输入的信息，负责从视图读取数据，控制用户输入，并向模型发送数据，是应用程序中处理用户交互的部分。

示意图如下：

```
input event  +------+   view controll   +------------+
-----------> | View | ----------------> | Controller |
             +------+                   +------------+
                |															^
                |															|
                | update model								| change notification
                |															|
                |															|
                V															|
             +-------+												|
             | Model | <----------------------/
             +-------+       query model
```

### Spring MVC的特点

与传统mvc相比，Spring mvc是Spring生态下的mvc，因此有很多方便程序员理解和使用的特性。

- 清晰的角色划分：mvc模式中的各个角色都用具体的类来表示，面向对象的程度很高。
- 配置方式很灵活，很多配置的可以自由定制：框架类和应用程序类都能作为Java bean配置，诸如绑定（binding）和验证（validation）以及handler mapping等都可以自由定制，满足各种业务需求。
- 针对性支持jsp：拥有可定制的本地化和主题解析、简单而强大的jsp标签库以及jsp表单标签库。
- 可使用Spring bean的特性，并委托给ioc框架管理（生命周期被限制为`request`或者`session`）

## 怎么解决问题

Spring mvc是Spring框架生态内的内容，因此其工作是需要依赖Spring的（我认为主要是让Spring帮忙管理它的各个组件）。工作流程示意图：

![](https://tcs.teambition.net/storage/311y5891748626b30b706100d536e0dc16a2?Signature=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJBcHBJRCI6IjU5Mzc3MGZmODM5NjMyMDAyZTAzNThmMSIsIl9hcHBJZCI6IjU5Mzc3MGZmODM5NjMyMDAyZTAzNThmMSIsIl9vcmdhbml6YXRpb25JZCI6IiIsImV4cCI6MTYwNjEwOTIwMCwiaWF0IjoxNjA1NTA0NDAwLCJyZXNvdXJjZSI6Ii9zdG9yYWdlLzMxMXk1ODkxNzQ4NjI2YjMwYjcwNjEwMGQ1MzZlMGRjMTZhMiJ9.4zKyFlebj0QwyU3bT5QowHTotr0D3it_qIR5Vo6G6fU)

对应到实际的工程中，可以理解为前端发来的所有请求首先都是到达`DispatcherServlet`，然后它通过`HandlerMapping`匹配并申请调用（通过诸如`@RequestMapping`以及其中的api地址、请求方法匹配）相应业务的`Controller`（调用的实际上是有`@Controller`注解的类中被匹配到的方法），并把这个申请调度`Controller`的请求发送给 `HandlerAdapter`，`HandlerAdapter`会集中管理调度所有的 `Controller`来实现业务逻辑，当具体的`Controller`接到命令时就会通过经典的三层架构来实现业务功能（ `Service`， `Repository`，层层调用），然后返回一个`ModelAndView`（或者`Model`，二者差别不大，在返回时的设置上有些不同）给`HandlerAdapter`再传给 `DispatcherServlet`，此时`DispatcherServlet`把收到的`ModelAndView`丢给`ViewResolver`解析，解析结果传回`DispatcherServlet`后最后呈现给用户 `View`。

可以看出上图中1、7、8、9、10、11、12就是之前mvc概念中的三角关系图，Spring mvc是将mvc模式嵌入了自己的生态中，因此能够让Spring框架介入，帮忙更好的管理细节，从而让mvc模式发挥出其强大的作用。

## 在前后端分离项目中的应用

我认为Spring mvc的本意是希望程序员能够将前端和后端写在一个工程内，每次用户请求后返回一个整体的页面给客户，但由于每次需要返回一个视图使得其不太能应对高并发、高吞吐量的情况。因此可以取其精华，去其糟粕，只保留其对controller层的管理功能，而model和view可以采用专门的前端框架实现（大部分前端框架都有专门的性能优化和更好的用户体验），此外，采用前后端分离开发可以进一步保证整个工程的开发能够“齐头并进”。

基于以上原因，我们小组的项目也是采用前（vue）后端分离的结构。