class Simplex {
    // column is larger than raw.
    int[] a;
    double[][] element;
    int[] xB;
    double[] cB;
    double[] cJ;
    int[] unbase;
    double[] b_test;
    double[] x_test;
    public Simplex(double[][] element, int raw, int column, double[] cJ){
        xB = new int[raw];
        cB = new double[raw];
        unbase = new int[column-raw-1];
        b_test = new double[raw];
        x_test = new double[column];
        this.element =element;
        a = new int[] {raw, column};
        this.cJ = cJ;
        for(int i=0,j=column - raw; j<column; i++, j++)
            xB[i] = j;
        for(int i=0; i<column-raw-1; i++)
            unbase[i] = i+1;
        System.arraycopy(this.cJ, xB[0]-1, cB, 0, xB.length);
    }
    // Gaussian elimination
    public void GaussTran(){
        for(int i=0; i<a[0]; i++){
            for(int j=0; j<a[1]; j++)
                if(j!=xB[i]){
                    element[i][j] /= element[i][xB[i]]==0 ? 1 : element[i][xB[i]];
                }
            element[i][xB[i]] /= element[i][xB[i]]==0 ? 1 : element[i][xB[i]];
            if(element[i][xB[i]] == 0){
                System.out.println("That LP question has no solution.");
                System.exit(1);
            }
            for(int k=0; k<a[0]; k++){
                if(k!=i){
                    double ele = element[k][xB[i]];
                    for(int l=0; l<a[1]; l++)
                        element[k][l] -= element[i][l]*ele;
                }
            }
        }
    }
    public boolean checkAndIter(){
        // check[0] go in, check[1] go out.
        int[] check = new int[] {0,0};
        x_test = new double[a[1]];
        double compare = 0;
        double bi = 1000000;
        // X-test
        for (int i : unbase) {
            x_test[i] = cJ[i - 1];
            for (int j = 0; j < xB.length; j++)
                x_test[i] -= cB[j] * element[j][i];
            if (compare < x_test[i]) {
                compare = x_test[i];
                check[0] = compare == 0 ? 0 : i;
            }
        }
        if(check[0] == 0)
            return false;
        // B-test (1000000 refers to minus or infinite bi/aik)
        for(int i=0; i<xB.length; i++){
            if(element[i][0]==0){
                check[1] = xB[i];
                break;
            }
            b_test[i] = element[i][0]/element[i][check[0]]>=0 ? element[i][0]/element[i][check[0]]:1000000;
            if(b_test[i] < bi) {
                bi = b_test[i];
                check[1] = xB[i];
            }
        }
        if(check[1] == 0)
            return false;
        // refresh xB, unbase
        for(int i=0; i<xB.length || i<unbase.length;i++){
            if(i<xB.length) {
                xB[i] = xB[i] == check[1] ? check[0] : xB[i];
                cB[i] = cJ[xB[i] - 1];
            }
            if(i<unbase.length) {
                if(unbase[i] == check[0])
                    unbase[i] = check[1];
            }
        }return true;
    }
    public void show(int sign){
        // show the whole info of the question
        System.out.print("b \t\t\t");
        for(int j=-1; j<a[0]+1;j++){
            for(int i=0; i<a[1]; i++){
                if(j == -1 && i<a[1]-1)
                    System.out.printf("x%d\t\t\t", i + 1);
                else if (j < a[0] && j > -1)
                    System.out.printf("%-8.2f \t", element[j][i]);
                else if (j==a[0] && i==0)
                    System.out.print("X-test\t\t");
                else if (j==a[0])
                    System.out.printf("%-8.2f \t", x_test[i]);
            }
            if(j==-1)
                System.out.print("B-test \t\t");
            else if(j < a[0] && sign != 1)
                System.out.printf("%.2f \t", b_test[j]);
            else
                System.out.print("\\\\ \t");
            System.out.print("\n");
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
    }
    public double[] calculate(){
        // The best deal stored in result, and the last one of result is max z.
        double[] result = new double[element[0].length+1];
        // Main iter
        while(true){
            GaussTran();
            if(!checkAndIter())
                break;
            show(0);
        }show(1);
        System.out.println("Best solution:");
        for(int i=0; i<xB.length || i<element[0].length+1; i++) {
            if (i < xB.length) {
                System.out.printf("x%d : %.2f\n", xB[i], element[i][0]);
                result[xB[i]] = element[i][0];
                result[element[0].length] += cJ[xB[i] - 1] * element[i][0];
            }
        }return result;
    }
}