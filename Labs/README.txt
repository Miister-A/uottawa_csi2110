I have attempted to perform task 2 by converting the implementation of the LinkList class from a single-linked list into a doubly-linked list but I kept encountering the same crash error :

Exception in thread "main" java.lang.IllegalAccessError: class LinkList tried to access field Node.nodeNext (LinkList is in unnamed module of loader com.sun.tools.javac.launcher.Main$MemoryClassLoader @2aece37d; Node is in unnamed module of loader 'app')
        at LinkList.addToEnd(LinkList.java:29)
        at LinkList.main(LinkList.java:73)