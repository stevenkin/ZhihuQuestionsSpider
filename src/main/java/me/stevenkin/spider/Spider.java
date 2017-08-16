package me.stevenkin.spider;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by wjg on 2017/5/9.
 */
public class Spider implements Runnable {

    private Downloader downloader;
    private PageParser pageParser;
    private Storer storer;

    private BlockingQueue<Request> requests;
    private ExecutorService executor;
    private int threadNum;

    public Spider(){
        this.requests = new LinkedBlockingQueue<>();
    }

    public Spider downloader(Downloader downloader){
        this.downloader = downloader;
        return this;
    }

    public Spider pageParser(PageParser pageParser){
        this.pageParser = pageParser;
        return this;
    }

    public Spider storer(Storer storer){
        this.storer = storer;
        return this;
    }

    public Spider thread(int num){
        this.executor = Executors.newFixedThreadPool(num);
        this.threadNum = num;
        return this;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Request request = requests.take();
                Page page = downloader.download(request);
                //System.out.println("page is "+page);
                Result result = pageParser.parsePage(page);
                System.out.println("result is "+result);
                result.setPage(page);
                if(!result.isSkip()){
                    List<Request> requestList = result.getRequests();
                    if(requestList!=null&&!requestList.isEmpty()){
                        for(Request request1:requestList){
                            this.requests.put(request1);
                        }
                    }
                }
                System.out.println("requests size is "+this.requests.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000+ new Random().nextInt(500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Spider start(Request request) throws InterruptedException {
        this.requests.put(request);
        return this;
    }

    public void go(){
        for(int i=0;i<this.threadNum;i++){
            this.executor.submit(this);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Spider spider = new Spider();
        spider.downloader(new Downloader())
                .pageParser(new PageParser())
                .storer(new Storer())
                .thread(3);
        spider.start(new Request("https://www.zhihu.com/topics",null));
        spider.go();

    }
}
