package indi.lisen;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("start");
		for (int i = 0; i < 10; i++) {
			Thread a = new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						System.out.println(
								Thread.currentThread().getName() + "sleep 5s");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			});
			a.setName("thread-" + i);
			a.start();
		}

		while (true) {
			System.out.println("sleep 5s");
			Thread.sleep(5000);
		}
	}
}
