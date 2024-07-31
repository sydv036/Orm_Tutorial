package orm.thi_final_orm.controller;

import orm.thi_final_orm.common.ValidationCommon;
import orm.thi_final_orm.entities.Task;
import orm.thi_final_orm.entities.TaskAssignment;
import orm.thi_final_orm.entities.Workker;
import orm.thi_final_orm.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainController {
    private static Scanner scanner = new Scanner(System.in);
    private static ITaskService taskService = new TaskServiceImpl();
    private static IWorkkerService workkerService = new WorkkerServiceImpl();
    private static ITaskAssignmentService taskAssignmentService = new TaskAssignmentServiceImpl();

    public static void main(String[] args) {
        try {
            insertData();
            menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void menu() {
        while (true) {
            System.out.println("================MENU================");
            System.out.println("1. Display Giam doc/ tong giam doc");
            System.out.println("2. Display quan li");
            System.out.println("3. Display nhan vien thuong");
            System.out.println("4. Search bu name task");
            System.out.println("5. Phan cong task");
            System.out.println("0.Exist ");
            System.out.println("================MENU================");
            System.out.print("Please select a function: ");
            String value = scanner.nextLine();
            switch (value) {
                case "1" -> {
                    List<Workker> workkerListTGD = workkerService.getAllWorkerByLevel(4);
                    List<Workker> workkerListGD = workkerService.getAllWorkerByLevel(3);
                    display(workkerListTGD);
                    display(workkerListGD);
                }
                case "2" -> {
                    List<Workker> workkerListQL = workkerService.getAllWorkerByLevel(2);
                    display(workkerListQL);
                }
                case "3" -> {
                    List<Workker> workkerListNV = workkerService.getAllWorkerByLevel(1);
                    display(workkerListNV);
                }
                case "4" -> searchByName();
                case "5" -> phanConCongViec();
                case "0" -> {
                    return;
                }
                default -> System.err.println("Dose not exist!");
            }
        }
    }

    private static void phanConCongViec() {
        System.out.print("Ban la ai, nhap ma nhan vien: ");
        String maNV = scanner.nextLine();
        Integer maNVParse = ValidationCommon.integerParse(maNV);
        if (maNVParse != null) {
            Workker workker = workkerService.getByid(maNVParse);
            if (workker != null) {
                if ("1".equals(String.valueOf(workker.getLevel()))) {
                    System.err.println("Ban khong phai la quan li");
                } else {
                    if ("2".equals(String.valueOf(workker.getLevel()))) {
                        List<Workker> workkerList = workkerService.getAllWorkerByLevel(1);
                        display(workkerList);
                        System.out.print("Vui long chon nhan vien muon giao viec(Ma NV): ");
                        String maNVGiaoTask = scanner.nextLine();
                        Integer maNVGiaoTaskNew = ValidationCommon.integerParse(maNVGiaoTask);
                        Workker workkerDuocGiaoTask = workkerService.getByid(maNVGiaoTaskNew);
                        List<Task> taskList = taskService.getAllData();
                        displayTaskList(taskList);
                        System.out.print("Vui long chon task muon giao(Ma task): ");
                        String maTaskGiao = scanner.nextLine();
                        Integer maTaskGiaoParen = ValidationCommon.integerParse(maTaskGiao);
                        Task taskMuonGiao = taskService.getByid(maTaskGiaoParen);
                        if (taskMuonGiao.getPriority() > workkerDuocGiaoTask.getLevel()) {
                            System.err.println("Cong viec " + taskMuonGiao.getName() + " khong phu hop voi nhan vien: " + workkerDuocGiaoTask.getName());
                        } else {
                            if (workkerDuocGiaoTask != null && taskMuonGiao != null) {
                                TaskAssignment isCheckTaskAssignment = taskAssignmentService.getTaskAssignmentByTask(taskMuonGiao);
                                if (isCheckTaskAssignment != null) {
                                    System.err.println("Cong viec nay da duoc nhan vien: " + isCheckTaskAssignment.getWorkker().getName() + " dam nhan");
                                } else {
                                    System.out.print("Vui long nhap ngay bat dau task(yyyy-MM-dd): ");
                                    String startDateTask = scanner.nextLine();
                                    System.out.print("Vui long nhap ngay ket thuc task(yyyy-MM-dd): ");
                                    String endDateTask = scanner.nextLine();
                                    LocalDate startDateTaskParse = ValidationCommon.localDateParse(startDateTask);
                                    LocalDate endDateTaskParse = ValidationCommon.localDateParse(endDateTask);
                                    TaskAssignment taskAssignmentCurrent = taskAssignmentService.getTaskAssignmentByWorkker(workkerDuocGiaoTask);
                                    if (taskAssignmentCurrent != null) {

                                        if (startDateTaskParse.isAfter(taskAssignmentCurrent.getStartDate()) || endDateTaskParse.isBefore(taskAssignmentCurrent.getEndDate())) {

                                            if (taskAssignmentCurrent.getTask().getPriority() < taskMuonGiao.getPriority()) {
                                                if (startDateTaskParse != null && endDateTaskParse != null) {
                                                    TaskAssignment taskAssignmentGiao = TaskAssignment.builder()
                                                            .id(taskAssignmentCurrent.getId())
                                                            .workker(workkerDuocGiaoTask)
                                                            .task(taskMuonGiao)
                                                            .startDate(startDateTaskParse)
                                                            .endDate(endDateTaskParse)
                                                            .build();
                                                    taskAssignmentService.saveOrUpdate(taskAssignmentGiao);
                                                }
                                            } else {
                                                System.err.println("Nhan vien: " + taskAssignmentCurrent.getWorkker().getName() + " khong the dam nhan con viec trong thoi gian tu " + ValidationCommon.localDateParseToString(startDateTaskParse) + " den " + ValidationCommon.localDateParseToString(endDateTaskParse));
                                            }
                                        } else {
                                            if (startDateTaskParse != null && endDateTaskParse != null) {
                                                TaskAssignment taskAssignmentGiao = TaskAssignment.builder()
                                                        .workker(workkerDuocGiaoTask)
                                                        .task(taskMuonGiao)
                                                        .startDate(startDateTaskParse)
                                                        .endDate(endDateTaskParse)
                                                        .build();
                                                taskAssignmentService.saveOrUpdate(taskAssignmentGiao);
                                            }
                                        }
                                    } else {
                                        if (startDateTaskParse != null && endDateTaskParse != null) {
                                            TaskAssignment taskAssignmentGiao = TaskAssignment.builder()
                                                    .workker(workkerDuocGiaoTask)
                                                    .task(taskMuonGiao)
                                                    .startDate(startDateTaskParse)
                                                    .endDate(endDateTaskParse)
                                                    .build();
                                            taskAssignmentService.saveOrUpdate(taskAssignmentGiao);
                                        }
                                    }


                                }
                            } else {
                                System.err.println("Nhan vien muon giao viec khong ton tai!");
                            }
                        }

                    } else if ("3".equals(String.valueOf(workker.getLevel()))) {
                        List<Workker> workkerListNV = workkerService.getAllWorkerByLevel(1);
                        List<Workker> workkerListQL = workkerService.getAllWorkerByLevel(2);
                        display(workkerListNV);
                        display(workkerListQL);
                        System.out.print("Vui long chon nhan vien muon giao viec(Ma NV): ");
                        String maNVGiaoTask = scanner.nextLine();
                        Integer maNVGiaoTaskNew = ValidationCommon.integerParse(maNVGiaoTask);
                        Workker workkerDuocGiaoTask = workkerService.getByid(maNVGiaoTaskNew);
                        List<Task> taskList = taskService.getAllData();
                        displayTaskList(taskList);
                        System.out.print("Vui long chon task muon giao(Ma task): ");
                        String maTaskGiao = scanner.nextLine();
                        Integer maTaskGiaoParen = ValidationCommon.integerParse(maTaskGiao);
                        Task taskMuonGiao = taskService.getByid(maTaskGiaoParen);
                        if (taskMuonGiao.getPriority() > workkerDuocGiaoTask.getLevel()) {
                            System.err.println("Cong viec " + taskMuonGiao.getName() + " khong phu hop voi nhan vien: " + workkerDuocGiaoTask.getName());
                        } else {
                            if (workkerDuocGiaoTask != null && taskMuonGiao != null) {
                                TaskAssignment isCheckTaskAssignment = taskAssignmentService.getTaskAssignmentByTask(taskMuonGiao);
                                if (isCheckTaskAssignment != null) {
                                    System.err.println("Cong viec nay da duoc nhan vien: " + isCheckTaskAssignment.getWorkker().getName() + " dam nhan");
                                } else {
                                    System.out.print("Vui long nhap ngay bat dau task(yyyy-MM-dd): ");
                                    String startDateTask = scanner.nextLine();
                                    System.out.print("Vui long nhap ngay ket thuc task(yyyy-MM-dd): ");
                                    String endDateTask = scanner.nextLine();
                                    LocalDate startDateTaskParse = ValidationCommon.localDateParse(startDateTask);
                                    LocalDate endDateTaskParse = ValidationCommon.localDateParse(endDateTask);
                                    TaskAssignment taskAssignmentCurrent = taskAssignmentService.getTaskAssignmentByWorkker(workkerDuocGiaoTask);
                                    if (taskAssignmentCurrent != null) {
                                        if (startDateTaskParse.isAfter(taskAssignmentCurrent.getStartDate()) || endDateTaskParse.isBefore(taskAssignmentCurrent.getEndDate())) {
                                            if (taskAssignmentCurrent.getTask().getPriority() < taskMuonGiao.getPriority()) {
                                                if (startDateTaskParse != null && endDateTaskParse != null) {
                                                    TaskAssignment taskAssignmentGiao = TaskAssignment.builder()
                                                            .id(taskAssignmentCurrent.getId())
                                                            .workker(workkerDuocGiaoTask)
                                                            .task(taskMuonGiao)
                                                            .startDate(startDateTaskParse)
                                                            .endDate(endDateTaskParse)
                                                            .build();
                                                    taskAssignmentService.saveOrUpdate(taskAssignmentGiao);
                                                }
                                            } else {
                                                System.err.println("Nhan vien: " + taskAssignmentCurrent.getWorkker().getName() + " khong the dam nhan con viec trong thoi gian tu " + ValidationCommon.localDateParseToString(startDateTaskParse) + " den " + ValidationCommon.localDateParseToString(endDateTaskParse));
                                            }
                                        } else {
                                            if (startDateTaskParse != null && endDateTaskParse != null) {
                                                TaskAssignment taskAssignmentGiao = TaskAssignment.builder()
                                                        .workker(workkerDuocGiaoTask)
                                                        .task(taskMuonGiao)
                                                        .startDate(startDateTaskParse)
                                                        .endDate(endDateTaskParse)
                                                        .build();
                                                taskAssignmentService.saveOrUpdate(taskAssignmentGiao);
                                            }
                                        }
                                    } else {
                                        if (startDateTaskParse != null && endDateTaskParse != null) {
                                            TaskAssignment taskAssignmentGiao = TaskAssignment.builder()
                                                    .workker(workkerDuocGiaoTask)
                                                    .task(taskMuonGiao)
                                                    .startDate(startDateTaskParse)
                                                    .endDate(endDateTaskParse)
                                                    .build();
                                            taskAssignmentService.saveOrUpdate(taskAssignmentGiao);
                                        }
                                    }


                                }
                            } else {
                                System.err.println("Nhan vien muon giao viec khong ton tai!");
                            }
                        }

                    } else {
                        List<Workker> workkerListNV = workkerService.getAllWorkerByLevel(1);
                        List<Workker> workkerListQL = workkerService.getAllWorkerByLevel(2);
                        List<Workker> workkerListGD = workkerService.getAllWorkerByLevel(3);
                        display(workkerListNV);
                        display(workkerListQL);
                        display(workkerListGD);
                        System.out.print("Vui long chon nhan vien muon giao viec(Ma NV): ");
                        String maNVGiaoTask = scanner.nextLine();
                        Integer maNVGiaoTaskNew = ValidationCommon.integerParse(maNVGiaoTask);
                        Workker workkerDuocGiaoTask = workkerService.getByid(maNVGiaoTaskNew);
                        List<Task> taskList = taskService.getAllData();
                        displayTaskList(taskList);
                        System.out.print("Vui long chon task muon giao(Ma task): ");
                        String maTaskGiao = scanner.nextLine();
                        Integer maTaskGiaoParen = ValidationCommon.integerParse(maTaskGiao);
                        Task taskMuonGiao = taskService.getByid(maTaskGiaoParen);
                        if (taskMuonGiao.getPriority() > workkerDuocGiaoTask.getLevel()) {
                            System.err.println("Cong viec " + taskMuonGiao.getName() + " khong phu hop voi nhan vien: " + workkerDuocGiaoTask.getName());
                        } else {
                            if (workkerDuocGiaoTask != null && taskMuonGiao != null) {
                                TaskAssignment isCheckTaskAssignment = taskAssignmentService.getTaskAssignmentByTask(taskMuonGiao);
                                if (isCheckTaskAssignment != null) {
                                    System.err.println("Cong viec nay da duoc nhan vien: " + isCheckTaskAssignment.getWorkker().getName() + " dam nhan");
                                } else {
                                    System.out.print("Vui long nhap ngay bat dau task(yyyy-MM-dd): ");
                                    String startDateTask = scanner.nextLine();
                                    System.out.print("Vui long nhap ngay ket thuc task(yyyy-MM-dd): ");
                                    String endDateTask = scanner.nextLine();
                                    LocalDate startDateTaskParse = ValidationCommon.localDateParse(startDateTask);
                                    LocalDate endDateTaskParse = ValidationCommon.localDateParse(endDateTask);
                                    TaskAssignment taskAssignmentCurrent = taskAssignmentService.getTaskAssignmentByWorkker(workkerDuocGiaoTask);
                                    if (taskAssignmentCurrent != null) {
                                        if (startDateTaskParse.isAfter(taskAssignmentCurrent.getStartDate()) || endDateTaskParse.isBefore(taskAssignmentCurrent.getEndDate())) {
                                            if (taskAssignmentCurrent.getTask().getPriority() < taskMuonGiao.getPriority()) {
                                                if (startDateTaskParse != null && endDateTaskParse != null) {
                                                    TaskAssignment taskAssignmentGiao = TaskAssignment.builder()
                                                            .id(taskAssignmentCurrent.getId())
                                                            .workker(workkerDuocGiaoTask)
                                                            .task(taskMuonGiao)
                                                            .startDate(startDateTaskParse)
                                                            .endDate(endDateTaskParse)
                                                            .build();
                                                    taskAssignmentService.saveOrUpdate(taskAssignmentGiao);
                                                }
                                            } else {
                                                System.err.println("Nhan vien: " + taskAssignmentCurrent.getWorkker().getName() + " khong the dam nhan con viec trong thoi gian tu " + ValidationCommon.localDateParseToString(startDateTaskParse) + " den " + ValidationCommon.localDateParseToString(endDateTaskParse));
                                            }
                                        } else {
                                            if (startDateTaskParse != null && endDateTaskParse != null) {
                                                TaskAssignment taskAssignmentGiao = TaskAssignment.builder()
                                                        .workker(workkerDuocGiaoTask)
                                                        .task(taskMuonGiao)
                                                        .startDate(startDateTaskParse)
                                                        .endDate(endDateTaskParse)
                                                        .build();
                                                taskAssignmentService.saveOrUpdate(taskAssignmentGiao);
                                            }
                                        }
                                    } else {
                                        if (startDateTaskParse != null && endDateTaskParse != null) {
                                            TaskAssignment taskAssignmentGiao = TaskAssignment.builder()
                                                    .workker(workkerDuocGiaoTask)
                                                    .task(taskMuonGiao)
                                                    .startDate(startDateTaskParse)
                                                    .endDate(endDateTaskParse)
                                                    .build();
                                            taskAssignmentService.saveOrUpdate(taskAssignmentGiao);
                                        }
                                    }


                                }
                            } else {
                                System.err.println("Nhan vien muon giao viec khong ton tai!");
                            }
                        }

                    }
                }
            } else {
                System.err.println("Khong ton tai nhan vien");
            }

        }
    }

    private static Boolean checkCoTheDamNhanCongViec(Workker workker, LocalDate startDateNew, LocalDate endDateNew) {
        TaskAssignment taskAssignment = taskAssignmentService.getTaskAssignmentByWorkker(workker);
        if (taskAssignment != null) {
            if (startDateNew.isAfter(taskAssignment.getStartDate()) ||
                    endDateNew.isBefore(taskAssignment.getEndDate())
            ) {
                System.out.println("Nhan vien: " + taskAssignment.getWorkker().getName() + " khong the dam nhan con viec trong thoi gian tu " + startDateNew + " den " + endDateNew);
                return false;
            }
            return true;
        }
        return false;
    }

    private static void searchByName() {
        System.out.print("Please enter task name: ");
        String taskName = scanner.nextLine();
        List<Task> tasks = taskService.searchByName(taskName);
        if (tasks.size() > 0) {
            displayTaskList(tasks);
        } else {
            System.err.println("Data is updating");
        }
    }

    private static void displayTaskList(List<Task> taskList) {
        for (Task item : taskList) {
            System.out.println(item);
        }
    }

    private static void display(List<Workker> workkerList) {
        for (Workker item : workkerList) {
            System.out.println(item);
        }
    }

    private static void insertData() {
        Task task1 = Task.builder()
                .name("TASK1")
                .priority(1)
                .build();
        Task task2 = Task.builder()
                .name("TASK2")
                .priority(2)
                .build();
        Task task3 = Task.builder()
                .name("TASK3")
                .priority(3)
                .build();
        Task task4 = Task.builder()
                .name("TASK4")
                .priority(4)
                .build();
        Task task5 = Task.builder()
                .name("TASK5")
                .priority(2)
                .build();
        Task task6 = Task.builder()
                .name("TASK1")
                .priority(1)
                .build();
        Task task7 = Task.builder()
                .name("TASK7")
                .priority(2)
                .build();
        Task task8 = Task.builder()
                .name("TASK8")
                .priority(3)
                .build();
        Task task9 = Task.builder()
                .name("TASK9")
                .priority(4)
                .build();
        Task task10 = Task.builder()
                .name("TASK10")
                .priority(4)
                .build();
        taskService.saveOrUpdate(task1);
        taskService.saveOrUpdate(task2);
        taskService.saveOrUpdate(task3);
        taskService.saveOrUpdate(task4);
        taskService.saveOrUpdate(task5);
        taskService.saveOrUpdate(task6);
        taskService.saveOrUpdate(task7);
        taskService.saveOrUpdate(task8);
        taskService.saveOrUpdate(task9);
        taskService.saveOrUpdate(task10);

        Workker workker1 = Workker.builder()
                .name("Workker1")
                .bDate(ValidationCommon.localDateParse("2015-01-01"))
                .gender("male")
                .salary(30000000.0)
                .level(4)
                .build();
        workkerService.saveOrUpdate(workker1);
        Workker workker2 = Workker.builder()
                .name("Workker2")
                .bDate(ValidationCommon.localDateParse("2015-01-02"))
                .gender("male")
                .salary(3000000.0)
                .level(1)
                .supperWorkerId(String.valueOf(workker1.getId()))
                .build();
        Workker workker3 = Workker.builder()
                .name("Workker3")
                .bDate(ValidationCommon.localDateParse("2015-01-03"))
                .gender("male")
                .salary(11000000.0)
                .level(2)
                .supperWorkerId(String.valueOf(workker1.getId()))
                .build();
        Workker workker4 = Workker.builder()
                .name("Workker4")
                .bDate(ValidationCommon.localDateParse("2015-01-04"))
                .gender("male")
                .salary(7000000.0)
                .level(1)
                .build();
        Workker workker5 = Workker.builder()
                .name("Workker5")
                .bDate(ValidationCommon.localDateParse("2015-01-05"))
                .gender("male")
                .salary(10000000.0)
                .level(2)
                .build();
        Workker workker6 = Workker.builder()
                .name("Workker6")
                .bDate(ValidationCommon.localDateParse("2015-01-06"))
                .gender("male")
                .salary(9000000.0)
                .level(1)
                .build();
        Workker workker7 = Workker.builder()
                .name("Workker7")
                .bDate(ValidationCommon.localDateParse("2015-01-07"))
                .gender("male")
                .salary(50000000.0)
                .level(4)
                .build();
        workkerService.saveOrUpdate(workker7);
        Workker workker8 = Workker.builder()
                .name("Workker8")
                .bDate(ValidationCommon.localDateParse("2015-01-08"))
                .gender("male")
                .salary(25000000.0)
                .level(3)
                .supperWorkerId(String.valueOf(workker7.getId()))
                .build();
        Workker workker9 = Workker.builder()
                .name("Workker9")
                .bDate(ValidationCommon.localDateParse("2015-01-09"))
                .gender("male")
                .salary(15000000.0)
                .level(2)
                .build();
        Workker workker10 = Workker.builder()
                .name("Workker10")
                .bDate(ValidationCommon.localDateParse("2015-01-10"))
                .gender("male")
                .salary(3000000.0)
                .level(1)
                .supperWorkerId(String.valueOf(workker7.getId()))
                .build();

        workkerService.saveOrUpdate(workker2);
        workkerService.saveOrUpdate(workker3);
        workkerService.saveOrUpdate(workker4);
        workkerService.saveOrUpdate(workker5);
        workkerService.saveOrUpdate(workker6);
        workkerService.saveOrUpdate(workker8);
        workkerService.saveOrUpdate(workker9);
        workkerService.saveOrUpdate(workker10);

        TaskAssignment taskAssignment1 = TaskAssignment.builder()
                .task(task2)
                .workker(workker3)
                .startDate(ValidationCommon.localDateParse("2024-07-01"))
                .endDate(ValidationCommon.localDateParse("2024-07-05"))
                .build();
        taskAssignmentService.saveOrUpdate(taskAssignment1);
    }
}
