# async-framework

async-framework是基于google开源框架Disruptor开发的一个异步流程处理框架，关于Disruptor的介绍请参考 https://github.com/LMAX-Exchange/disruptor

async-framework提供了流程和队列的概念，流程 Flow 代表步骤，队列 Queue 代表处理节点，队列由Disruptor封装而成，每个事件都有Flow发起，并且在每个Queue存在一个状态，每个队列处理特定的事件，简单的来说 Flow 是在多个队列处理的上下文信息。

# 用法

参考测试用例 com.lefu.async.test.QueueTest

# wiki
https://github.com/leo27lijiang/async-framework/wiki
