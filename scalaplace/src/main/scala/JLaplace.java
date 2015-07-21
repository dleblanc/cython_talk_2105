public class JLaplace {

    public static void laplace(double[][] arr, double dx2, double dy2) {

        for (int i = 1; i < arr.length - 1; i++) {

            for (int j = 1; j < arr[i].length - 1; j++) {

                arr[i][j] =
                    ((arr[i + 1][j] + arr[i - 1][j]) * dx2 +
                    (arr[i][j + 1] + arr[i][j - 1] * dx2)) /
                    (2 * (dx2 + dy2));
            }
        }
    }
}
