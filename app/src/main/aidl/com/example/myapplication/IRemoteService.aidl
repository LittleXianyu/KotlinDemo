// IRemoteService.aidl
package com.example.myapplication;
import com.example.myapplication.Book;

// Declare any non-default types here with import statements
// https://developer.android.com/guide/components/aidl?hl=zh-cn#java
/**
*  1.service端确保线程安全
    2.客户端需要在子线程去访问远程调用接口，因为RPC调用是同步的，耗时可能较长
    3.服务端的异常不会反馈给客户端
* **/
interface IRemoteService {
    List<Book> getBookList();
    void addBookInOut(inout Book book);
}