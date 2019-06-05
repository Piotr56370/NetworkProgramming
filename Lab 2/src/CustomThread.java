class CustomThread extends Thread {

    // Name of the thread
    private String name;
    // The nodes after which the current thread runs
    private CustomThread[] nodes = new CustomThread[0];
    // End flag
    private boolean ended = false;

    CustomThread(String name) {
        this.name = name;
    }

    void setEdges(CustomThread... nodes) {
        this.nodes = new CustomThread[nodes.length];
        int it = 0;
        for (CustomThread node : nodes) {
            this.nodes[it++] = node;
        }
    }

    boolean hasEnded() {
        return ended;
    }

    private void checkNode(int i) {
        synchronized (this.nodes[i]) {
            try {
                if (!this.nodes[i].hasEnded()) {
                    this.nodes[i].wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void endRun() {
        this.notify();
        this.ended = true;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.nodes.length; i++) {
            checkNode(i);
        }

        System.out.println("Thread " + this.name + " executed!!");
        endRun();
    }
}