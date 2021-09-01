class Animal implements Runnable {
    private String name;
    private int position;
    private int speed;
    private int restMax;
    private static boolean winner = false;

    public Animal(String name, int position, int speed, int restMax) {
        this.name = name;
        this.position = position;
        this.speed = speed;
        this.restMax = restMax;
    }

    public void run() {
        while (!winner) {
            System.out.println("Animal: " + name + "   Position: " + position);
            try {
                Thread.sleep((long) Math.random() * (restMax - 0) + restMax);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            position++;
            if (position >= 100) {
                finish();
                System.out.println("Winner is: " + name);
            }
        }
    }

    public void finish() {
        winner = true;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getRestMax() {
        return this.restMax;
    }

    public void setRestMax(int restMax) {
        this.restMax = restMax;
    }
}
