package com.bruteforces.demo.utils;

import  java.io.*;
import  java.io.File;

import org.springframework.core.io.ClassPathResource;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.lang.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import java.lang.reflect.Field;

public class Client {
    /**
     * 在线IDE接口
     * @param timeLimit 时间限制，单位为毫秒
     * @param memLimit  内存限制，单位为兆
     * @param language  语言
     * @param subInfo   用户代码存放路径，就是SubmissionController里面传入的subInfo
     * @param subSrc    用户源代码，String
     * @param userInput 用户输入，String
     * @return  一个map，键"result"对应着一个SubmitResult对象，存放结果；键"output"对应着一个String，存放用户输出
     * @throws IOException
     * @throws InterruptedException
     */
    public static Map<String, Object> onlineIDE(long timeLimit, long memLimit, String language, String subInfo, String subSrc, String userInput) throws IOException, InterruptedException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Properties address = new Properties();
        address.load(new ClassPathResource("address.properties").getInputStream());
        // 存放用户代码的主路径
        String codeAddr = address.getProperty("codeAddr");
        // 运行平台 Windows/Linux
        String platform = address.getProperty("platform");
        String usrDir = codeAddr + "/" + subInfo;
        // 用户输入与用户代码放在同一路径下
        String ansAddr = usrDir;
        File file = new File(usrDir);
        if (!file.exists()) {
            file.mkdir();
        }
        // 重定向系统输出流以及错误流至文件
        PrintStream out = System.out;
        PrintStream err = System.err;
        File log = new File(usrDir + "/log");
        if(platform.equals("Linux")) {
            if (!log.exists()) {
                log.createNewFile();
            }
            log.setExecutable(true);
            log.setReadable(true);
            log.setWritable(true);
        }
        File compileErr = new File(usrDir + "/compileErr");
        if(platform.equals("Linux")) {
            if (!compileErr.exists()) {
                compileErr.createNewFile();
            }
            compileErr.setExecutable(true);
            compileErr.setReadable(true);
            compileErr.setWritable(true);
        }
        PrintStream ps = new PrintStream(new FileOutputStream(log));
//        System.setOut(ps);
//        System.setErr(ps);
        String format = "";
        switch (language) {
            case "C":
                format = "main.c";
                break;
            case "C++":
                format = "main.cpp";
                break;
            case "Java":
                format = "Main.java";
                break;
            default:

        }
        File codetxt = new File(usrDir + "/" + format);
        if(platform.equals("Linux")) {
            if (!codetxt.exists()) {
                codetxt.createNewFile();
            }
            codetxt.setExecutable(true);
            codetxt.setReadable(true);
            codetxt.setWritable(true);
        }
        PrintWriter pfp = new PrintWriter(codetxt);
        pfp.print(subSrc);
        pfp.close();

        File userIn = new File(usrDir + "/data1.in");
        if(platform.equals("Linux")) {
            if (!userIn.exists()) {
                userIn.createNewFile();
            }
            userIn.setExecutable(true);
            userIn.setReadable(true);
            userIn.setWritable(true);
        }
        pfp = new PrintWriter(userIn);
        pfp.print(userInput);
        pfp.close();

        long maxTimeLimit = 10*1000;
        long maxMemLimit = 64;
        if(timeLimit >= maxTimeLimit) {
            timeLimit = maxTimeLimit;
        }
        if(memLimit >= maxMemLimit) {
            memLimit = maxMemLimit;
        }

        memLimit *= 1024;
        SubmitResult sR = new SubmitResult(judge(1, timeLimit, memLimit, language, usrDir, ansAddr, platform));
        sR.mem /= 1024;

        System.setOut(out);
        System.setErr(err);

        File userOut = new File(usrDir + "/data1.out");
        String userOutContentStr = "";

        if(userOut.exists()) {
            byte[] userOutContent = new byte[(int) userOut.length()];

            FileInputStream in = new FileInputStream(userOut);
            in.read(userOutContent);
            in.close();

            String charset = "UTF-8";
            userOutContentStr = new String(userOutContent, charset);
        }

        if(sR.result.equals("Compile Error")) {
            FileInputStream fin = new FileInputStream(compileErr);
            byte[] errContent = new byte[(int) compileErr.length()];
            fin.read(errContent);
            fin.close();
            String charset = "UTF-8";
            userOutContentStr = new String(errContent, charset);
            userOutContentStr += "Compile Error";
        }

        resultMap.put("result", sR);
        resultMap.put("output", userOutContentStr);

        return resultMap;
    }
    /**
     * 为用户代码创建文件夹存放，并judge
     * @param problemId
     * @param numTestPoint
     * @param timeLimit millisecond
     * @param memLimit  megabyte
     * @param language
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static SubmitResult preprocess(String problemId, int numTestPoint, long timeLimit, long memLimit, String language, String subInfo, String subSrc) throws IOException, InterruptedException {
        Properties address = new Properties();
        address.load(new ClassPathResource("address.properties").getInputStream());
        // 标准答案路径
        String ansAddr = address.getProperty("ansAddr");
        ansAddr += "/"+problemId;
        // 存放用户代码的主路径
        String codeAddr = address.getProperty("codeAddr");
        // 运行平台 Windows/Linux
        String platform = address.getProperty("platform");
        String usrDir = codeAddr + "/" + subInfo;
        File file = new File(usrDir);
        if (!file.exists()) {
            file.mkdir();
        }
        // 重定向系统输出流以及错误流至文件
        PrintStream out = System.out;
        PrintStream err = System.err;
        File log = new File(usrDir + "/log");
        if(platform.equals("Linux")) {
            if (!log.exists()) {
                log.createNewFile();
            }
            log.setExecutable(true);
            log.setReadable(true);
            log.setWritable(true);
        }
        File compileErr = new File(usrDir + "/compileErr");
        if(platform.equals("Linux")) {
            if (!compileErr.exists()) {
                compileErr.createNewFile();
            }
            compileErr.setExecutable(true);
            compileErr.setReadable(true);
            compileErr.setWritable(true);
        }
        PrintStream ps = new PrintStream(new FileOutputStream(log));
//        System.setOut(ps);
//        System.setErr(ps);
        String format = "";
        switch (language) {
            case "C":
                format = "main.c";
                break;
            case "C++":
                format = "main.cpp";
                break;
            case "Java":
                format = "Main.java";
                break;
            default:

        }
        File codetxt = new File(usrDir + "/" + format);
        if(platform.equals("Linux")) {
            if (!codetxt.exists()) {
                codetxt.createNewFile();
            }
            codetxt.setExecutable(true);
            codetxt.setReadable(true);
            codetxt.setWritable(true);
        }
        PrintWriter pfp = new PrintWriter(codetxt);
        pfp.print(subSrc);
        pfp.close();

        memLimit *= 1024;
        SubmitResult sR = new SubmitResult(judge(numTestPoint, timeLimit, memLimit, language, usrDir, ansAddr, platform));
        sR.mem /= 1024;

        System.setOut(out);
        System.setErr(err);

        return sR;
    }
    /**
     * 先编译，若编译通过，则执行
     * @param numTestPoint
     * @param timeLimit millisecond
     * @param memLimit  megabyte
     * @param language
     * @param codeAddr
     * @param ansAddr
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static SubmitResult judge(int numTestPoint, long timeLimit, long memLimit, String language, String codeAddr, String ansAddr, String platform) throws IOException, InterruptedException {
        long maxTime = 0, maxMem = 0;
        long time = 0, mem = 0;
        SubmitResult info = new SubmitResult();
        info.result = "Waiting";

        int returnCode = 0;
        returnCode = Client.compile(language, codeAddr, platform);
        if(returnCode != 0) {
            info.result = "Compile Error";
        } else {
            for(int i = 1; i <= numTestPoint; i++) {
                info.result = "Running";
                try {
                    info = programJudge(i, timeLimit, memLimit, language, codeAddr, ansAddr, platform);
                } catch (Exception e) {
                    e.printStackTrace();
                    info.result = "System Error";
                    break;
                }
                if(maxTime < info.time) {
                    maxTime = info.time;
                }
                if(maxMem < info.mem) {
                    maxMem = info.mem;
                }
                if(!info.result.equals("Accepted")) {
                    break;
                }
            }
            info.time = maxTime;
            info.mem = maxMem;
        }

        return info;
    }

    public static int compile(String language, String address, String platform) throws IOException, InterruptedException {
        String cmd = "";
        switch (language) {
            case "C":
                cmd = "gcc -g main.c -o main -Wall -lm -O2 -std=c99 --static -DONLINE_JUDGE";
                break;
            case "C++":
                cmd = "g++ -g main.cpp -o main -Wall -lm -O2 --static -DONLINE_JUDGE";
                break;
            case "Java":
                cmd = "javac Main.java";
                break;
            default:
        }

        String[] exe=new String[]{ ""};
        switch (platform) {
            case "Windows":
                exe=new String[]{ "cmd", "/c", "cd " + address + " && " + cmd};
                break;
            case "Linux":
                exe=new String[]{ "sh", "-c", "cd " + address + " && " + cmd};
                break;
            default:

        }
        ProcessBuilder processBuilder = new ProcessBuilder(exe);
        processBuilder.redirectOutput(new File(address + "/compileErr"));
        processBuilder.redirectError(new File(address + "/compileErr"));
        Process process = processBuilder.start();
        int num1 = process.waitFor();
//        InputStreamReader ir = new InputStreamReader(process.getInputStream());
//        FileOutputStream fout = new FileOutputStream(new File(address + "/compileErr"));
//        int tmp;
//        while((tmp = ir.read()) != -1) {
//            fout.write((char)tmp);
//            System.out.print((char)tmp);
//        }
//        ir.close();
        int num2 = process.exitValue();
//        System.out.println(num1);
//        System.out.println(num2);
        return num2;
    }

    /**
     * 创建子进程运行用户程序，同时监控子进程，若超时/超内存则杀死
     * @param testPoint
     * @param timeLimit millisecond
     * @param memLimit  kilobyte
     * @param language
     * @param codeAddr
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static SubmitResult programJudge(int testPoint, long timeLimit, long memLimit, String language, String codeAddr, String ansAddr, String platform) throws IOException, InterruptedException {
        SubmitResult info = new SubmitResult();
        String cmd = "";
//        File input = new File(ansAddr+"/"+problemId+"/data"+testPoint+".in");
        File correctIn = new File(ansAddr+"/data"+testPoint+".in");
        File userAns = new File(codeAddr+"/data"+testPoint+".out");
//        File correctAns = new File(ansAddr+"/"+problemId+"/data"+testPoint+".out");
        File correctOut = new File(ansAddr+"/data"+testPoint+".out");
        int pid = 0;

        switch (language) {
            case "C":
            case "C++":
                cmd = "./main";
                break;
            case "Java":
                cmd = "java Main";
                break;
            default:
        }

        long maxMem = 0, curMem = 0, curTime = 0, startTime = 0;
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();

        String[] exe=new String[]{ ""};
        switch (platform) {
            case "Windows":
                exe=new String[]{ "cmd", "/c", "cd " + codeAddr + " && " + cmd};
                break;
            case "Linux":
                exe=new String[]{ "sh", "-c", "cd " + codeAddr + " && " + cmd};
                break;
            default:

        }
        ProcessBuilder processBuilder = new ProcessBuilder(exe);
        processBuilder.redirectInput(correctIn);
        processBuilder.redirectOutput(userAns);
        Process process = processBuilder.start();

        if (process.getClass().getName().equals("java.lang.Win32Process") || process.getClass().getName().equals("java.lang.ProcessImpl")) {
            Field f = null;
            try {
                f = process.getClass().getDeclaredField("handle");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            f.setAccessible(true);
            long handl = 0;
            try {
                handl = f.getLong(process);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Kernel32 kernel = Kernel32.INSTANCE;
            WinNT.HANDLE hand = new WinNT.HANDLE();
            hand.setPointer(Pointer.createConstant(handl));
            pid = kernel.GetProcessId(hand);
            f.setAccessible(false);
        }
        //for unix based operating systems
        else if (process.getClass().getName().equals("java.lang.UNIXProcess")) {
            Field f = null;
            try {
                f = process.getClass().getDeclaredField("pid");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            f.setAccessible(true);
            try {
                pid = (int) f.getLong(process);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            f.setAccessible(false);
        }
//        } else {
//            pid = (int) process.pid();
//        }

        OSProcess osprocess = os.getProcess(pid);
        try {
            startTime = osprocess.getStartTime();
        } catch (NullPointerException e) {
            startTime = System.currentTimeMillis();
            curTime = startTime;
        }
        while(osprocess != null) {
            try {
                osprocess.updateAttributes();
            } catch (NullPointerException e) {}
//            curMem = osprocess.getVirtualSize();
            curMem = osprocess.getResidentSetSize();
            curTime = System.currentTimeMillis();
//            System.out.println(curMem + " " + (curTime-startTime));
            if(curMem > maxMem) {
                maxMem = curMem;
            }
            if(maxMem > memLimit*1024*8) {
                info.time = curTime-startTime;
                info.mem = maxMem/1024/8;
                info.result = "Memory Limit Exceed";
                return info;
            }
            if(curTime-startTime > timeLimit) {
                info.time = curTime-startTime;
                info.mem = maxMem/1024/8;
                info.result = "Time Limit Exceed";
                return info;
            }
            if(process.isAlive() == false && process.exitValue() == 0) {
                info.time = curTime-startTime;
                info.mem = maxMem/1024/8;
                break;
            }
            if(process.isAlive() == false && process.exitValue() != 0) {
                info.time = curTime-startTime;
                info.mem = maxMem/1024/8;
                info.result = "Runtime Error";
                return info;
            }
        }

        if(codeAddr.equals(ansAddr)) {
            info.result = "onlineIDE";
        } else {
            // check answer
            byte[] userContent = new byte[(int) userAns.length()];
            byte[] correctContent = new byte[(int) correctOut.length()];
            String user = null, correct = null;

            FileInputStream in = new FileInputStream(userAns);
            in.read(userContent);
            in.close();
            in = new FileInputStream(correctOut);
            in.read(correctContent);
            in.close();

            String charset = "UTF-8";
            user = new String(userContent, charset);
            correct = new String(correctContent, charset);

            if (user.equals(correct)) {
                info.result = "Accepted";
            } else if (user.replaceAll("\\s*", "").equals(correct.replaceAll("\\s*", ""))) {
                info.result = "Presentation Error";
            } else if (user.contains(correct)) {
                info.result = "Output Limit Exceed";
            } else {
                info.result = "Wrong Answer";
            }
        }

        return info;
    }
}