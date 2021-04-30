public class test {
    public static void main(String[] args){
        Simplex a = new Simplex(new double[][] {{40, 2, 3, 1, 0, 0},{40, 3, 2, 0, 1, 0},{20, 1, 1, 0, 0, 1}}, 3, 6,new double[] {8, 10, 0, 0, 0});
		Simplex b = new Simplex(new double[][] {{4, 4, 2, 2, -1, 0, 0},{20, 2, 4, 0, 0, 1, 0},{16, 4, 8, 2, 0, 0, 1}}, 3, 7,new double[] {2, 1, 1, 0, 0, 0});
        Simplex c = new Simplex(new double[][] {{15,2,3,1,1,0,0},{24,3,1,2,0,1,0},{18,2,2,3,0,0,1}}, 3, 7, new double[] {2, 4, 3, 0, 0, 0});
        Simplex d = new Simplex(new double[][] {{-23, -4, -4, 0, 1, 0},{4, 2, 1, 0, 0, 1},{-5, -1, -1, 1, 0, 0}}, 3, 6, new double[] {4, 5, 1, 0, 0});
        double[] result1 = a.calculate();
        System.out.printf("Best answer is: %.2f \n", result1[a.element[0].length]);
		double[] result2 = b.calculate();
		System.out.printf("Best answer is: %.2f \n", result2[b.element[0].length]);
		double[] result3 = c.calculate();
		System.out.printf("Best answer is: %.2f \n", result3[c.element[0].length]);
		double[] result4 = d.calculate();
		System.out.printf("Best answer is: %.2f \n", result4[c.element[0].length]);
    }
}
