/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 * <p>
 * 项目名：	springCloud
 * 文件名：	HystrixTest.java
 * 模块说明：
 * 修改历史：
 * 2020/8/2 - taozhi - 创建。
 */
package com.snail.feignconsumer.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author taozhi
 */
public class HystrixTest extends HystrixCommand {

  protected HystrixTest(HystrixCommandGroupKey group) {
    super(group);
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) {
    //	HystrixTest hystrixTest = new HystrixTest(HystrixCommandGroupKey.Factory.asKey("ext"));
    /**
     * execute()：以同步阻塞方式执行run()。以demo为例，调用execute()后，
     * hystrix先创建一个新线程运行run()，
     * 	接着调用程序要在execute()调用处一直阻塞着，直到run()运行完成
     */
    //	System.out.println("result:" + hystrixTest.execute());

    /**
     * queue()：以异步非阻塞方式执行run()。以demo为例，
     * 	一调用queue()就直接返回一个Future对象，
     * 	同时hystrix创建一个新线程运行run()，
     * 	调用程序通过Future.get()拿到run()的返回结果，
     * 	而Future.get()是阻塞执行的
     */
    Future<String> futureResult = new HystrixTest(HystrixCommandGroupKey.Factory.asKey("ext")).queue();
    String result = "";
    try {
      result = futureResult.get();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ExecutionException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    System.out.println("程序结果："+result);
  }

  /**
   * 相当于 try
   * @return
   * @throws Exception
   */
  @Override
  protected Object run() throws Exception {
    System.out.println("执行逻辑");
    // int i = 1/0; // 异常自动调用 getFallback 方法
    int i = 1/1; // 非异常往下执行
    return "ok";
  }

  /**
   * 相当于 catch
   * @return
   */
  @Override
  protected Object getFallback() {
    return "getFallbackgetFallback";
  }
}
