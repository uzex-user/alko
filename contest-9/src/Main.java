import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int n;
    static int[][] costMatrix;
    static int[] assignmentWorkerToTask;
    static int[] assignmentTaskToWorker;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(reader.readLine());
        costMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            String[] parts = reader.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                costMatrix[i][j] = Integer.parseInt(parts[j]);
            }
        }

        assignmentWorkerToTask = new int[n];
        assignmentTaskToWorker = new int[n];
        Arrays.fill(assignmentWorkerToTask, -1);
        Arrays.fill(assignmentTaskToWorker, -1);

        int minTotalCost = solveAssignmentProblem();

        System.out.println(minTotalCost);

        for (int worker = 0; worker < n; worker++) {
            int task = assignmentWorkerToTask[worker];
            System.out.println((worker + 1) + " " + (task + 1));
        }
    }

    static int solveAssignmentProblem() {
        int[] labelWorker = new int[n];
        int[] labelTask = new int[n];
        Arrays.fill(labelWorker, 0);
        Arrays.fill(labelTask, 0);

        for (int worker = 0; worker < n; worker++) {
            int[] minSlack = new int[n];
            int[] minSlackWorker = new int[n];
            Arrays.fill(minSlack, Integer.MAX_VALUE);
            boolean[] usedWorker = new boolean[n];
            boolean[] usedTask = new boolean[n];
            int[] parent = new int[n];
            Arrays.fill(parent, -1);

            int currentWorker = worker;
            int committedTask = -1;

            while (true) {
                usedWorker[currentWorker] = true;

                for (int task = 0; task < n; task++) {
                    if (!usedTask[task]) {
                        int slack = costMatrix[currentWorker][task] - labelWorker[currentWorker] - labelTask[task];
                        if (slack < minSlack[task]) {
                            minSlack[task] = slack;
                            minSlackWorker[task] = currentWorker;
                        }
                    }
                }

                int delta = Integer.MAX_VALUE;
                for (int task = 0; task < n; task++) {
                    if (!usedTask[task] && minSlack[task] < delta) {
                        delta = minSlack[task];
                        committedTask = task;
                    }
                }

                for (int i = 0; i < n; i++) {
                    if (usedWorker[i]) {
                        labelWorker[i] += delta;
                    }
                    if (usedTask[i]) {
                        labelTask[i] -= delta;
                    } else {
                        minSlack[i] -= delta;
                    }
                }

                usedTask[committedTask] = true;
                int assignedWorker = assignmentTaskToWorker[committedTask];
                if (assignedWorker == -1) {
                    while (committedTask != -1) {
                        int prevWorker = minSlackWorker[committedTask];
                        int prevTask = assignmentWorkerToTask[prevWorker];
                        assignmentWorkerToTask[prevWorker] = committedTask;
                        assignmentTaskToWorker[committedTask] = prevWorker;
                        committedTask = prevTask;
                    }
                    break;
                } else {
                    currentWorker = assignedWorker;
                }
            }
        }

        int totalCost = 0;
        for (int worker = 0; worker < n; worker++) {
            totalCost += costMatrix[worker][assignmentWorkerToTask[worker]];
        }
        return totalCost;
    }
}
