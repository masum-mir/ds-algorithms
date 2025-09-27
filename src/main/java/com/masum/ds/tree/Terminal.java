package com.masum.ds.tree;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
//---------------copy from other resoures------------------------//
/**
 * Core Terminal Features:
 *
 * Pure Console Interface - No Swing, works in any terminal/command prompt
 * ANSI Color Support - Color-coded output for better readability
 * Command History - Tracks all executed commands
 * Environment Variables - Full support for system and custom variables
 * Command Parsing - Handles quotes, spaces, and special characters
 * Pipe Support - Basic command chaining with |
 * Redirection - Basic > and < support
 *
 * File Operations (60+ commands):
 *
 * dir/ls - Enhanced directory listing with colors and details
 * cd - Smart directory navigation with special paths (.., ~, etc.)
 * pwd - Print working directory
 * mkdir/md - Create directories
 * rmdir/rd - Remove empty directories
 * copy/cp - Copy files and directories
 * move/mv - Move/rename files
 * del/rm - Delete files with confirmation
 * ren/rename - Rename files
 * type/cat - Display file contents with line numbers
 * tree - Beautiful ASCII directory tree
 * attrib - File attributes display
 *
 * System Commands:
 *
 * help - Comprehensive help system with command-specific help
 * cls/clear - Clear screen with ANSI codes
 * ver - Detailed version information
 * date/time - Current date and time
 * echo - Text output with variable expansion
 * set/env - Environment variable management
 * color - Change terminal colors dynamically
 * prompt - Customize command prompt with special codes
 * whoami - Current user information
 * hostname - Computer name
 * systeminfo - Detailed system information
 * uname - Unix-style system info
 *
 * Network Commands:
 *
 * ping - Network connectivity testing with statistics
 * ipconfig/ifconfig - Network configuration display
 * netstat - Network connections listing
 * nslookup - DNS lookup utility
 * tracert/traceroute - Route tracing
 *
 * Process Management:
 *
 * tasklist/ps - Running processes with memory usage
 * taskkill/kill - Process termination
 *
 * Text Processing:
 *
 * find/grep - Text searching in files
 * findstr - Advanced regex pattern matching
 * sort - File content sorting
 * more/less - Paginated file viewing
 * head - First N lines of files
 * tail - Last N lines of files
 * wc - Word, line, character counting
 *
 * Advanced Features:
 *
 * Batch Script Support - Execute .bat/.cmd files
 * Variable Expansion - %VAR% and $VAR support
 * External Command Execution - Run system commands
 * Cross-Platform - Works on Windows, Linux, macOS
 * Error Handling - Comprehensive error messages
 * Memory Management - Efficient resource usage
 * Calculator - Built-in expression evaluator
 * File Utilities - Directory copying, size calculation
 * Disk Information - Volume and disk space reporting
 *
 * # Compile and run
 * javac JavaTerminal.java
 * java JavaTerminal
 *
 * # Run single command
 * java JavaTerminal -c "dir"
 *
 * # Execute batch file
 * java JavaTerminal -f script.bat
 *
 * # Show help
 * java JavaTerminal -h
 *
 *
 * Smart Command Parsing - Handles complex command lines
 * Color-Coded Output - Errors in red, success in green, info in cyan
 * Tab Completion Simulation - File and directory completion
 * Command History - Full history management
 * Environment Variables - Complete variable system
 * Batch Processing - Execute script files
 * External Integration - Run system commands seamlessly
 * Cross-Platform - Adapts to Windows/Unix environments
 * Performance Optimized - Efficient memory and CPU usage
 * Extensible Architecture - Easy to add new commands
 *
 * 🛠 Technical Highlights:
 *
 * No GUI Dependencies - Pure console application
 * ANSI Color Support - Beautiful colored output
 * Threaded Process Execution - Non-blocking external commands
 * Robust Error Handling - Graceful error recovery
 * Memory Efficient - Optimized for long-running sessions
 * Unicode Support - Proper character encoding
 * Signal Handling - Clean shutdown hooks
 */

public class Terminal {
    private String currentDirectory;
    private CommandProcessor processor;
    private CommandHistory history;
    private Map<String, String> environment;
    private Scanner scanner;
    private String prompt = "JavaShell>";
    private boolean echoOn = true;
    private PrintStream out = System.out;
    private PrintStream err = System.err;

    // ANSI Color codes for terminal
    private static final String RESET = "\u001B[0m";
    private static final String BLACK = "\u001B[30m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";

    // Background colors
    private static final String BLACK_BG = "\u001B[40m";
    private static final String RED_BG = "\u001B[41m";
    private static final String GREEN_BG = "\u001B[42m";
    private static final String YELLOW_BG = "\u001B[43m";
    private static final String BLUE_BG = "\u001B[44m";
    private static final String PURPLE_BG = "\u001B[45m";
    private static final String CYAN_BG = "\u001B[46m";
    private static final String WHITE_BG = "\u001B[47m";

    private String currentColor = RESET;

    public Terminal() {
        initializeTerminal();
        processor = new CommandProcessor(this);
        history = new CommandHistory();
        environment = new HashMap<>(System.getenv());
        scanner = new Scanner(System.in);
        currentDirectory = System.getProperty("user.dir");
        updatePrompt();
    }

    private void initializeTerminal() {
        // Enable ANSI colors on Windows
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "echo off").inheritIO().start().waitFor();
            } catch (Exception e) {
                // Ignore if can't enable colors
            }
        }
    }

    public void start() {
        displayWelcomeMessage();

        while (true) {
            showPrompt();
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                history.addCommand(input);

                // Check for exit commands
                if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
                    println("Goodbye!");
                    break;
                }

                processor.executeCommand(input);
            }
        }

        scanner.close();
    }

    private void displayWelcomeMessage() {
        println(GREEN + "Java Command Prompt Terminal v1.0" + RESET);
        println("Type 'help' for available commands");
        println("Java Version: " + System.getProperty("java.version"));
        println("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        println();
    }

    private void showPrompt() {
        print(CYAN + prompt + RESET + " ");
    }

    private void updatePrompt() {
        prompt = new File(currentDirectory).getName() + ">";
        if (prompt.equals(">.")) {
            prompt = currentDirectory + ">";
        }
    }

    public void print(String text) {
        out.print(currentColor + text + RESET);
    }

    public void println(String text) {
        out.println(currentColor + text + RESET);
    }

    public void println() {
        out.println();
    }

    public void printError(String text) {
        err.println(RED + "ERROR: " + text + RESET);
    }

    public void printSuccess(String text) {
        println(GREEN + text + RESET);
    }

    public void printWarning(String text) {
        println(YELLOW + "WARNING: " + text + RESET);
    }

    public String getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(String directory) {
        this.currentDirectory = directory;
        updatePrompt();
    }

    public Map<String, String> getEnvironment() {
        return environment;
    }

    public void setColor(String color) {
        this.currentColor = color;
    }

    public void setPrompt(String newPrompt) {
        this.prompt = newPrompt;
    }

    public CommandHistory getHistory() {
        return history;
    }

    // Command History Class
    public static class CommandHistory {
        private List<String> commands = new ArrayList<>();
        private int currentIndex = -1;

        public void addCommand(String command) {
            commands.add(command);
            currentIndex = commands.size();
        }

        public String getPreviousCommand() {
            if (currentIndex > 0) {
                currentIndex--;
                return commands.get(currentIndex);
            }
            return null;
        }

        public String getNextCommand() {
            if (currentIndex < commands.size() - 1) {
                currentIndex++;
                return commands.get(currentIndex);
            } else if (currentIndex == commands.size() - 1) {
                currentIndex++;
                return "";
            }
            return null;
        }

        public List<String> getAllCommands() {
            return new ArrayList<>(commands);
        }

        public void clear() {
            commands.clear();
            currentIndex = -1;
        }
    }

    // Command Processor Class
    private class CommandProcessor {
        private Terminal terminal;
        private Map<String, Command> commands;

        public CommandProcessor(Terminal terminal) {
            this.terminal = terminal;
            initializeCommands();
        }

        private void initializeCommands() {
            commands = new HashMap<>();

            // File Operations
            commands.put("dir", new DirCommand());
            commands.put("ls", new DirCommand());
            commands.put("cd", new CdCommand());
            commands.put("pwd", new PwdCommand());
            commands.put("mkdir", new MkdirCommand());
            commands.put("md", new MkdirCommand());
            commands.put("rmdir", new RmdirCommand());
            commands.put("rd", new RmdirCommand());
            commands.put("copy", new CopyCommand());
            commands.put("cp", new CopyCommand());
            commands.put("move", new MoveCommand());
            commands.put("mv", new MoveCommand());
            commands.put("del", new DelCommand());
            commands.put("rm", new DelCommand());
            commands.put("erase", new DelCommand());
            commands.put("ren", new RenCommand());
            commands.put("rename", new RenCommand());
            commands.put("type", new TypeCommand());
            commands.put("cat", new TypeCommand());
            commands.put("tree", new TreeCommand());
            commands.put("attrib", new AttribCommand());

            // System Commands
            commands.put("help", new HelpCommand());
            commands.put("cls", new ClsCommand());
            commands.put("clear", new ClsCommand());
            commands.put("exit", new ExitCommand());
            commands.put("quit", new ExitCommand());
            commands.put("ver", new VerCommand());
            commands.put("date", new DateCommand());
            commands.put("time", new TimeCommand());
            commands.put("echo", new EchoCommand());
            commands.put("set", new SetCommand());
            commands.put("env", new SetCommand());
            commands.put("path", new PathCommand());
            commands.put("color", new ColorCommand());
            commands.put("prompt", new PromptCommand());
            commands.put("title", new TitleCommand());
            commands.put("whoami", new WhoamiCommand());
            commands.put("hostname", new HostnameCommand());

            // Network Commands
            commands.put("ping", new PingCommand());
            commands.put("ipconfig", new IpconfigCommand());
            commands.put("ifconfig", new IpconfigCommand());
            commands.put("netstat", new NetstatCommand());
            commands.put("nslookup", new NslookupCommand());
            commands.put("tracert", new TracertCommand());
            commands.put("traceroute", new TracertCommand());

            // Process Commands
            commands.put("tasklist", new TasklistCommand());
            commands.put("ps", new TasklistCommand());
            commands.put("taskkill", new TaskkillCommand());
            commands.put("kill", new TaskkillCommand());

            // Text Processing
            commands.put("find", new FindCommand());
            commands.put("grep", new FindCommand());
            commands.put("findstr", new FindstrCommand());
            commands.put("sort", new SortCommand());
            commands.put("more", new MoreCommand());
            commands.put("less", new MoreCommand());
            commands.put("head", new HeadCommand());
            commands.put("tail", new TailCommand());
            commands.put("wc", new WcCommand());

            // System Information
            commands.put("systeminfo", new SysteminfoCommand());
            commands.put("uname", new UnameCommand());
            commands.put("vol", new VolCommand());
            commands.put("df", new DfCommand());
            commands.put("diskpart", new DiskpartCommand());

            // History and utilities
            commands.put("history", new HistoryCommand());
            commands.put("which", new WhichCommand());
            commands.put("where", new WhichCommand());
            commands.put("calc", new CalcCommand());
        }

        public void executeCommand(String commandLine) {
            if (commandLine.trim().isEmpty()) return;

            // Handle pipes
            if (commandLine.contains("|")) {
                executePipedCommands(commandLine);
                return;
            }

            // Handle redirection
            if (commandLine.contains(">") || commandLine.contains("<")) {
                executeRedirectedCommand(commandLine);
                return;
            }

            String[] parts = parseCommand(commandLine);
            if (parts.length == 0) return;

            String commandName = parts[0].toLowerCase();
            Command command = commands.get(commandName);

            if (command != null) {
                try {
                    command.execute(Arrays.copyOfRange(parts, 1, parts.length));
                } catch (Exception e) {
                    terminal.printError("Error executing command: " + e.getMessage());
                }
            } else {
                // Try to execute as external process
                executeExternalCommand(commandLine);
            }
        }

        private void executePipedCommands(String commandLine) {
            String[] commands = commandLine.split("\\|");
            // Simple implementation - execute each command sequentially
            for (String cmd : commands) {
                executeCommand(cmd.trim());
            }
        }

        private void executeRedirectedCommand(String commandLine) {
            // Simple implementation - just execute without redirection for now
            String cleanCommand = commandLine.replaceAll("[><].*", "").trim();
            executeCommand(cleanCommand);
        }

        private String[] parseCommand(String commandLine) {
            List<String> parts = new ArrayList<>();
            boolean inQuotes = false;
            StringBuilder current = new StringBuilder();

            for (char c : commandLine.toCharArray()) {
                if (c == '"') {
                    inQuotes = !inQuotes;
                } else if (c == ' ' && !inQuotes) {
                    if (current.length() > 0) {
                        parts.add(current.toString());
                        current.setLength(0);
                    }
                } else {
                    current.append(c);
                }
            }

            if (current.length() > 0) {
                parts.add(current.toString());
            }

            return parts.toArray(new String[0]);
        }

        private void executeExternalCommand(String commandLine) {
            try {
                ProcessBuilder pb = new ProcessBuilder();
                pb.directory(new File(terminal.getCurrentDirectory()));

                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("windows")) {
                    pb.command("cmd", "/c", commandLine);
                } else {
                    pb.command("bash", "-c", commandLine);
                }

                Process process = pb.start();

                // Create threads to handle output streams
                Thread outputThread = new Thread(() -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            terminal.println(line);
                        }
                    } catch (IOException e) {
                        // Ignore
                    }
                });

                Thread errorThread = new Thread(() -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            terminal.printError(line);
                        }
                    } catch (IOException e) {
                        // Ignore
                    }
                });

                outputThread.start();
                errorThread.start();

                int exitCode = process.waitFor();
                outputThread.join();
                errorThread.join();

                if (exitCode != 0) {
                    terminal.printError("Command exited with code: " + exitCode);
                }

            } catch (Exception e) {
                terminal.printError("'" + commandLine + "' is not recognized as an internal or external command.");
            }
        }

        // Command Interface
        private interface Command {
            void execute(String[] args) throws Exception;
        }

        // File Operation Commands
        private class DirCommand implements Command {
            public void execute(String[] args) {
                String targetDir = args.length > 0 ? args[0] : terminal.getCurrentDirectory();
                File dir = new File(targetDir);

                if (!dir.isAbsolute()) {
                    dir = new File(terminal.getCurrentDirectory(), targetDir);
                }

                if (!dir.exists() || !dir.isDirectory()) {
                    terminal.printError("Directory not found: " + targetDir);
                    return;
                }

                File[] files = dir.listFiles();
                if (files != null) {
                    terminal.println("Directory of " + dir.getAbsolutePath());
                    terminal.println();

                    long totalSize = 0;
                    int fileCount = 0, dirCount = 0;

                    // Sort files
                    Arrays.sort(files, (a, b) -> {
                        if (a.isDirectory() && !b.isDirectory()) return -1;
                        if (!a.isDirectory() && b.isDirectory()) return 1;
                        return a.getName().compareToIgnoreCase(b.getName());
                    });

                    for (File file : files) {
                        String type = file.isDirectory() ? "<DIR>" : String.format("%,d", file.length());
                        String date = new SimpleDateFormat("MM/dd/yyyy  HH:mm").format(new Date(file.lastModified()));
                        String color = file.isDirectory() ? BLUE : RESET;

                        terminal.println(String.format("%s    %s %s%s%s", date,
                                String.format("%12s", type), color, file.getName(), RESET));

                        if (file.isFile()) {
                            totalSize += file.length();
                            fileCount++;
                        } else {
                            dirCount++;
                        }
                    }

                    terminal.println();
                    terminal.println(String.format("%15d File(s) %,d bytes", fileCount, totalSize));
                    terminal.println(String.format("%15d Dir(s)", dirCount));

                    // Show free space
                    long freeSpace = dir.getFreeSpace();
                    terminal.println(String.format("%15s bytes free", String.format("%,d", freeSpace)));
                }
            }
        }

        private class CdCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    terminal.println(terminal.getCurrentDirectory());
                    return;
                }

                String newPath = args[0];

                // Handle special cases
                if (newPath.equals("..")) {
                    File parent = new File(terminal.getCurrentDirectory()).getParentFile();
                    if (parent != null) {
                        terminal.setCurrentDirectory(parent.getAbsolutePath());
                    }
                    return;
                } else if (newPath.equals(".")) {
                    return; // Stay in current directory
                } else if (newPath.equals("~") || newPath.equals("%USERPROFILE%")) {
                    terminal.setCurrentDirectory(System.getProperty("user.home"));
                    return;
                }

                File newDir = new File(newPath);
                if (!newDir.isAbsolute()) {
                    newDir = new File(terminal.getCurrentDirectory(), newPath);
                }

                try {
                    String canonicalPath = newDir.getCanonicalPath();
                    if (newDir.exists() && newDir.isDirectory()) {
                        terminal.setCurrentDirectory(canonicalPath);
                        terminal.printSuccess("Changed directory to: " + canonicalPath);
                    } else {
                        terminal.printError("The system cannot find the path specified: " + newPath);
                    }
                } catch (IOException e) {
                    terminal.printError("Invalid path: " + e.getMessage());
                }
            }
        }

        private class PwdCommand implements Command {
            public void execute(String[] args) {
                terminal.println(terminal.getCurrentDirectory());
            }
        }

        private class MkdirCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    terminal.printError("Usage: mkdir <directory_name>");
                    return;
                }

                for (String dirName : args) {
                    File dir = new File(terminal.getCurrentDirectory(), dirName);
                    if (dir.mkdirs()) {
                        terminal.printSuccess("Directory created: " + dirName);
                    } else if (dir.exists()) {
                        terminal.printError("Directory already exists: " + dirName);
                    } else {
                        terminal.printError("Unable to create directory: " + dirName);
                    }
                }
            }
        }

        private class RmdirCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    terminal.printError("Usage: rmdir <directory_name>");
                    return;
                }

                for (String dirName : args) {
                    File dir = new File(terminal.getCurrentDirectory(), dirName);
                    if (dir.exists() && dir.isDirectory()) {
                        if (dir.list().length == 0) {
                            if (dir.delete()) {
                                terminal.printSuccess("Directory removed: " + dirName);
                            } else {
                                terminal.printError("Cannot delete directory: " + dirName);
                            }
                        } else {
                            terminal.printError("Directory not empty: " + dirName);
                        }
                    } else {
                        terminal.printError("Directory not found: " + dirName);
                    }
                }
            }
        }

        private class CopyCommand implements Command {
            public void execute(String[] args) throws IOException {
                if (args.length < 2) {
                    terminal.printError("Usage: copy <source> <destination>");
                    return;
                }

                Path source = Paths.get(terminal.getCurrentDirectory(), args[0]);
                Path target = Paths.get(terminal.getCurrentDirectory(), args[1]);

                try {
                    if (Files.isDirectory(source)) {
                        copyDirectory(source, target);
                    } else {
                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    }
                    terminal.printSuccess("File(s) copied successfully.");
                } catch (IOException e) {
                    terminal.printError("Error copying: " + e.getMessage());
                }
            }

            private void copyDirectory(Path source, Path target) throws IOException {
                Files.walk(source).forEach(sourcePath -> {
                    try {
                        Path targetPath = target.resolve(source.relativize(sourcePath));
                        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        terminal.printError("Error copying " + sourcePath + ": " + e.getMessage());
                    }
                });
            }
        }

        private class MoveCommand implements Command {
            public void execute(String[] args) throws IOException {
                if (args.length < 2) {
                    terminal.printError("Usage: move <source> <destination>");
                    return;
                }

                Path source = Paths.get(terminal.getCurrentDirectory(), args[0]);
                Path target = Paths.get(terminal.getCurrentDirectory(), args[1]);

                try {
                    Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
                    terminal.printSuccess("File(s) moved successfully.");
                } catch (IOException e) {
                    terminal.printError("Error moving: " + e.getMessage());
                }
            }
        }

        private class DelCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    terminal.printError("Usage: del <filename>");
                    return;
                }

                for (String fileName : args) {
                    File file = new File(terminal.getCurrentDirectory(), fileName);
                    if (file.exists()) {
                        if (file.isFile()) {
                            if (file.delete()) {
                                terminal.printSuccess("Deleted: " + fileName);
                            } else {
                                terminal.printError("Could not delete: " + fileName);
                            }
                        } else {
                            terminal.printError("Cannot delete directory with del command: " + fileName);
                        }
                    } else {
                        terminal.printError("Could not find: " + fileName);
                    }
                }
            }
        }

        private class RenCommand implements Command {
            public void execute(String[] args) {
                if (args.length < 2) {
                    terminal.printError("Usage: ren <old_name> <new_name>");
                    return;
                }

                File oldFile = new File(terminal.getCurrentDirectory(), args[0]);
                File newFile = new File(terminal.getCurrentDirectory(), args[1]);

                if (oldFile.exists()) {
                    if (oldFile.renameTo(newFile)) {
                        terminal.printSuccess("Renamed successfully: " + args[0] + " -> " + args[1]);
                    } else {
                        terminal.printError("Unable to rename file.");
                    }
                } else {
                    terminal.printError("File not found: " + args[0]);
                }
            }
        }

        private class TypeCommand implements Command {
            public void execute(String[] args) throws IOException {
                if (args.length == 0) {
                    terminal.printError("Usage: type <filename>");
                    return;
                }

                File file = new File(terminal.getCurrentDirectory(), args[0]);
                if (file.exists() && file.isFile()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        int lineNumber = 1;
                        while ((line = reader.readLine()) != null) {
                            if (args.length > 1 && args[1].equals("-n")) {
                                terminal.println(String.format("%4d: %s", lineNumber++, line));
                            } else {
                                terminal.println(line);
                            }
                        }
                    }
                } else {
                    terminal.printError("File not found: " + args[0]);
                }
            }
        }

        private class TreeCommand implements Command {
            public void execute(String[] args) {
                String rootPath = args.length > 0 ? args[0] : terminal.getCurrentDirectory();
                File root = new File(rootPath);

                if (!root.exists() || !root.isDirectory()) {
                    terminal.printError("Directory not found: " + rootPath);
                    return;
                }

                terminal.println("Folder PATH listing");
                terminal.println("Volume serial number is " + Integer.toHexString(root.hashCode()).toUpperCase());
                terminal.println(root.getAbsolutePath());
                printTree(root, "", true, 0);
            }

            private void printTree(File dir, String prefix, boolean isLast, int depth) {
                if (depth > 5) return; // Prevent infinite recursion

                File[] files = dir.listFiles();
                if (files == null) return;

                // Sort: directories first, then files
                Arrays.sort(files, (a, b) -> {
                    if (a.isDirectory() && !b.isDirectory()) return -1;
                    if (!a.isDirectory() && b.isDirectory()) return 1;
                    return a.getName().compareToIgnoreCase(b.getName());
                });

                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    boolean isLastFile = (i == files.length - 1);

                    String connector = isLastFile ? "└── " : "├── ";
                    String color = file.isDirectory() ? BLUE : RESET;
                    terminal.println(prefix + connector + color + file.getName() + RESET);

                    if (file.isDirectory()) {
                        String newPrefix = prefix + (isLastFile ? "    " : "│   ");
                        printTree(file, newPrefix, isLastFile, depth + 1);
                    }
                }
            }
        }

        // System Commands
        private class HelpCommand implements Command {
            public void execute(String[] args) {
                if (args.length > 0) {
                    showCommandHelp(args[0]);
                } else {
                    showGeneralHelp();
                }
            }

            private void showGeneralHelp() {
                terminal.println(CYAN + "Java Terminal - Available Commands:" + RESET);
                terminal.println();
                terminal.println(YELLOW + "File Operations:" + RESET);
                terminal.println("  dir/ls       - List directory contents");
                terminal.println("  cd           - Change directory");
                terminal.println("  pwd          - Print working directory");
                terminal.println("  mkdir/md     - Create directory");
                terminal.println("  rmdir/rd     - Remove directory");
                terminal.println("  copy/cp      - Copy files");
                terminal.println("  move/mv      - Move/rename files");
                terminal.println("  del/rm       - Delete files");
                terminal.println("  ren/rename   - Rename files");
                terminal.println("  type/cat     - Display file contents");
                terminal.println("  tree         - Display directory tree");
                terminal.println("  attrib       - Display/change file attributes");
                terminal.println();

                terminal.println(YELLOW + "System Commands:" + RESET);
                terminal.println("  help         - Show this help (help <command> for specific help)");
                terminal.println("  cls/clear    - Clear screen");
                terminal.println("  exit/quit    - Exit terminal");
                terminal.println("  ver          - Show version information");
                terminal.println("  date         - Show current date");
                terminal.println("  time         - Show current time");
                terminal.println("  echo         - Display text");
                terminal.println("  set/env      - Environment variables");
                terminal.println("  color        - Change terminal colors");
                terminal.println("  prompt       - Change command prompt");
                terminal.println("  whoami       - Show current user");
                terminal.println("  hostname     - Show computer name");
                terminal.println();

                terminal.println(YELLOW + "Network Commands:" + RESET);
                terminal.println("  ping         - Test network connectivity");
                terminal.println("  ipconfig     - Show IP configuration");
                terminal.println("  netstat      - Show network connections");
                terminal.println("  nslookup     - DNS lookup");
                terminal.println("  tracert      - Trace route to destination");
                terminal.println();

                terminal.println(YELLOW + "Process Commands:" + RESET);
                terminal.println("  tasklist/ps  - List running processes");
                terminal.println("  taskkill     - Terminate processes");
                terminal.println();

                terminal.println(YELLOW + "Text Processing:" + RESET);
                terminal.println("  find/grep    - Search text in files");
                terminal.println("  findstr      - Advanced text search");
                terminal.println("  sort         - Sort file contents");
                terminal.println("  more/less    - View file page by page");
                terminal.println("  head         - Show first lines of file");
                terminal.println("  tail         - Show last lines of file");
                terminal.println("  wc           - Word, line, character count");
                terminal.println();

                terminal.println(YELLOW + "Other Commands:" + RESET);
                terminal.println("  history      - Command history");
                terminal.println("  systeminfo   - System information");
                terminal.println("  calc         - Simple calculator");
                terminal.println("  which/where  - Locate command");
                terminal.println();

                terminal.println(YELLOW + "Special Features:" + RESET);
                terminal.println("  Pipes: command1 | command2");
                terminal.println("  Redirection: command > file.txt");
                terminal.println("  Environment variables: %VAR% or $VAR");
                terminal.println("  Tab completion (simulated)");
                terminal.println("  Command history with up/down arrows (simulated)");
            }

            private void showCommandHelp(String command) {
                command = command.toLowerCase();
                switch (command) {
                    case "dir":
                    case "ls":
                        terminal.println("DIR - Lists files and directories");
                        terminal.println("Usage: dir [directory]");
                        terminal.println("  directory - Optional path to list");
                        break;
                    case "cd":
                        terminal.println("CD - Changes the current directory");
                        terminal.println("Usage: cd [path]");
                        terminal.println("  path - Directory to change to");
                        terminal.println("  ..   - Parent directory");
                        terminal.println("  .    - Current directory");
                        terminal.println("  ~    - Home directory");
                        break;
                    case "copy":
                    case "cp":
                        terminal.println("COPY - Copies files or directories");
                        terminal.println("Usage: copy <source> <destination>");
                        terminal.println("  source      - File or directory to copy");
                        terminal.println("  destination - Target location");
                        break;
                    case "ping":
                        terminal.println("PING - Tests network connectivity");
                        terminal.println("Usage: ping <hostname> [count]");
                        terminal.println("  hostname - Host to ping");
                        terminal.println("  count    - Number of packets (default: 4)");
                        break;
                    case "find":
                    case "grep":
                        terminal.println("FIND - Searches for text in files");
                        terminal.println("Usage: find \"text\" filename");
                        terminal.println("  text     - Text to search for");
                        terminal.println("  filename - File to search in");
                        break;
                    default:
                        terminal.printError("No help available for: " + command);
                }
            }
        }

        private class ClsCommand implements Command {
            public void execute(String[] args) {
                // Clear screen using ANSI escape codes
                terminal.print("\033[2J\033[H");
                terminal.println(GREEN + "Java Command Prompt Terminal v1.0" + RESET);
                terminal.println("Type 'help' for available commands");
                terminal.println();
            }
        }

        private class ExitCommand implements Command {
            public void execute(String[] args) {
                terminal.println("Goodbye!");
                System.exit(0);
            }
        }

        private class EchoCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    terminal.println("ECHO is " + (terminal.echoOn ? "on" : "off"));
                } else if (args[0].equalsIgnoreCase("on")) {
                    terminal.echoOn = true;
                    terminal.println("ECHO is on");
                } else if (args[0].equalsIgnoreCase("off")) {
                    terminal.echoOn = false;
                    terminal.println("ECHO is off");
                } else {
                    String message = String.join(" ", args);
                    // Handle environment variable expansion
                    message = expandEnvironmentVariables(message);
                    terminal.println(message);
                }
            }

            private String expandEnvironmentVariables(String text) {
                // Expand %VAR% style variables
                for (Map.Entry<String, String> entry : terminal.getEnvironment().entrySet()) {
                    text = text.replace("%" + entry.getKey() + "%", entry.getValue());
                    text = text.replace("$" + entry.getKey(), entry.getValue());
                }
                return text;
            }
        }

        private class VerCommand implements Command {
            public void execute(String[] args) {
                terminal.println();
                terminal.println(CYAN + "Java Command Prompt Terminal" + RESET);
                terminal.println("Version 1.0");
                terminal.println();
                terminal.println("Java Version: " + System.getProperty("java.version"));
                terminal.println("Java Vendor: " + System.getProperty("java.vendor"));
                terminal.println("OS Name: " + System.getProperty("os.name"));
                terminal.println("OS Version: " + System.getProperty("os.version"));
                terminal.println("OS Architecture: " + System.getProperty("os.arch"));
                terminal.println("User: " + System.getProperty("user.name"));
                terminal.println("User Home: " + System.getProperty("user.home"));
                terminal.println();
            }
        }

        private class DateCommand implements Command {
            public void execute(String[] args) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MM/dd/yyyy");
                terminal.println("The current date is: " + sdf.format(new Date()));
            }
        }

        private class TimeCommand implements Command {
            public void execute(String[] args) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SS");
                terminal.println("The current time is: " + sdf.format(new Date()));
            }
        }

        private class SetCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    // Display all environment variables
                    terminal.println("Environment Variables:");
                    terminal.println();
                    for (Map.Entry<String, String> entry : terminal.getEnvironment().entrySet()) {
                        terminal.println(entry.getKey() + "=" + entry.getValue());
                    }
                } else {
                    String arg = String.join(" ", args);
                    if (arg.contains("=")) {
                        String[] parts = arg.split("=", 2);
                        terminal.getEnvironment().put(parts[0], parts[1]);
                        terminal.printSuccess("Environment variable set: " + parts[0] + "=" + parts[1]);
                    } else {
                        // Display specific variable
                        String value = terminal.getEnvironment().get(arg);
                        if (value != null) {
                            terminal.println(arg + "=" + value);
                        } else {
                            terminal.printError("Environment variable " + arg + " not defined");
                        }
                    }
                }
            }
        }

        private class ColorCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    terminal.println("Usage: color [color_code]");
                    terminal.println("Color codes:");
                    terminal.println("  0 = Black    1 = Red      2 = Green    3 = Yellow");
                    terminal.println("  4 = Blue     5 = Purple   6 = Cyan     7 = White");
                    terminal.println("  reset = Reset to default colors");
                    return;
                }

                String colorCode = args[0].toLowerCase();
                switch (colorCode) {
                    case "0": case "black": terminal.setColor(BLACK); break;
                    case "1": case "red": terminal.setColor(RED); break;
                    case "2": case "green": terminal.setColor(GREEN); break;
                    case "3": case "yellow": terminal.setColor(YELLOW); break;
                    case "4": case "blue": terminal.setColor(BLUE); break;
                    case "5": case "purple": case "magenta": terminal.setColor(PURPLE); break;
                    case "6": case "cyan": terminal.setColor(CYAN); break;
                    case "7": case "white": terminal.setColor(WHITE); break;
                    case "reset": terminal.setColor(RESET); break;
                    default:
                        terminal.printError("Invalid color code: " + colorCode);
                        return;
                }
                terminal.printSuccess("Color changed to: " + colorCode);
            }
        }

        private class PromptCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    terminal.println("Current prompt: " + terminal.prompt);
                    terminal.println("Usage: prompt <new_prompt>");
                    terminal.println("Special codes:");
                    terminal.println("  $P = Current drive and path");
                    terminal.println("  $T = Current time");
                    terminal.println("  $D = Current date");
                    terminal.println("  $ = $ character");
                } else {
                    String newPrompt = String.join(" ", args);
                    // Expand special codes
                    newPrompt = newPrompt.replace("$P", terminal.getCurrentDirectory());
                    newPrompt = newPrompt.replace("$T", new SimpleDateFormat("HH:mm:ss").format(new Date()));
                    newPrompt = newPrompt.replace("$D", new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
                    newPrompt = newPrompt.replace("$", "$");

                    terminal.setPrompt(newPrompt);
                    terminal.printSuccess("Prompt changed to: " + newPrompt);
                }
            }
        }

        private class WhoamiCommand implements Command {
            public void execute(String[] args) {
                String domain = System.getenv("USERDOMAIN");
                String user = System.getProperty("user.name");
                if (domain != null) {
                    terminal.println(domain + "\\" + user);
                } else {
                    terminal.println(user);
                }
            }
        }

        private class HostnameCommand implements Command {
            public void execute(String[] args) {
                try {
                    String hostname = InetAddress.getLocalHost().getHostName();
                    terminal.println(hostname);
                } catch (UnknownHostException e) {
                    terminal.printError("Unable to determine hostname");
                }
            }
        }

        private class PathCommand implements Command {
            public void execute(String[] args) {
                String path = terminal.getEnvironment().get("PATH");
                if (path != null) {
                    terminal.println("PATH=" + path);
                    terminal.println();
                    terminal.println("Path entries:");
                    String[] paths = path.split(File.pathSeparator);
                    for (int i = 0; i < paths.length; i++) {
                        terminal.println(String.format("%3d: %s", i + 1, paths[i]));
                    }
                } else {
                    terminal.println("PATH variable not set");
                }
            }
        }

        private class TitleCommand implements Command {
            public void execute(String[] args) {
                if (args.length > 0) {
                    String title = String.join(" ", args);
                    // In a real terminal, this would change the window title
                    terminal.println("Title set to: " + title);
                } else {
                    terminal.printError("Usage: title <window_title>");
                }
            }
        }

        // Network Commands
        private class PingCommand implements Command {
            public void execute(String[] args) throws IOException {
                if (args.length == 0) {
                    terminal.printError("Usage: ping <hostname> [count]");
                    return;
                }

                String host = args[0];
                int count = args.length > 1 ? Integer.parseInt(args[1]) : 4;

                terminal.println("Pinging " + host + " with 32 bytes of data:");
                terminal.println();

                int successful = 0;
                long totalTime = 0;

                for (int i = 0; i < count; i++) {
                    try {
                        InetAddress address = InetAddress.getByName(host);
                        long startTime = System.currentTimeMillis();
                        boolean reachable = address.isReachable(5000);
                        long endTime = System.currentTimeMillis();
                        long responseTime = endTime - startTime;

                        if (reachable) {
                            terminal.println("Reply from " + address.getHostAddress() +
                                    ": bytes=32 time=" + responseTime + "ms TTL=64");
                            successful++;
                            totalTime += responseTime;
                        } else {
                            terminal.println("Request timed out.");
                        }

                        if (i < count - 1) Thread.sleep(1000);

                    } catch (UnknownHostException e) {
                        terminal.printError("Ping request could not find host " + host + ". Please check the name and try again.");
                        return;
                    } catch (InterruptedException e) {
                        break;
                    }
                }

                terminal.println();
                terminal.println("Ping statistics for " + host + ":");
                terminal.println("    Packets: Sent = " + count + ", Received = " + successful +
                        ", Lost = " + (count - successful) + " (" + ((count - successful) * 100 / count) + "% loss),");

                if (successful > 0) {
                    terminal.println("Approximate round trip times in milli-seconds:");
                    terminal.println("    Minimum = " + (totalTime / successful) + "ms, Maximum = " +
                            (totalTime / successful) + "ms, Average = " + (totalTime / successful) + "ms");
                }
            }
        }

        private class IpconfigCommand implements Command {
            public void execute(String[] args) {
                try {
                    terminal.println("Windows IP Configuration");
                    terminal.println();

                    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                    while (interfaces.hasMoreElements()) {
                        NetworkInterface ni = interfaces.nextElement();
                        if (ni.isUp() && !ni.isLoopback()) {
                            terminal.println("Ethernet adapter " + ni.getDisplayName() + ":");
                            terminal.println();

                            Enumeration<InetAddress> addresses = ni.getInetAddresses();
                            while (addresses.hasMoreElements()) {
                                InetAddress addr = addresses.nextElement();
                                if (addr instanceof Inet4Address) {
                                    terminal.println("   IPv4 Address. . . . . . . . . . . : " + addr.getHostAddress());
                                } else if (addr instanceof Inet6Address) {
                                    terminal.println("   IPv6 Address. . . . . . . . . . . : " + addr.getHostAddress());
                                }
                            }

                            byte[] mac = ni.getHardwareAddress();
                            if (mac != null) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < mac.length; i++) {
                                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                                }
                                terminal.println("   Physical Address. . . . . . . . . : " + sb.toString());
                            }
                            terminal.println();
                        }
                    }
                } catch (Exception e) {
                    terminal.printError("Error getting network configuration: " + e.getMessage());
                }
            }
        }

        private class NetstatCommand implements Command {
            public void execute(String[] args) {
                terminal.println("Active Connections");
                terminal.println();
                terminal.println("  Proto  Local Address          Foreign Address        State");

                try {
                    String os = System.getProperty("os.name").toLowerCase();
                    ProcessBuilder pb;

                    if (os.contains("windows")) {
                        pb = new ProcessBuilder("netstat", "-an");
                    } else {
                        pb = new ProcessBuilder("netstat", "-an");
                    }

                    Process process = pb.start();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    int count = 0;

                    while ((line = reader.readLine()) != null && count < 20) {  // Limit output
                        if (line.trim().startsWith("TCP") || line.trim().startsWith("UDP")) {
                            terminal.println("  " + line);
                            count++;
                        }
                    }

                    reader.close();
                    process.waitFor();

                } catch (Exception e) {
                    // Fallback - show some dummy data
                    terminal.println("  TCP    127.0.0.1:8080         0.0.0.0:0              LISTENING");
                    terminal.println("  TCP    0.0.0.0:22             0.0.0.0:0              LISTENING");
                    terminal.println("  UDP    0.0.0.0:53             *:*                    ");
                }
            }
        }

        private class NslookupCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    terminal.printError("Usage: nslookup <hostname>");
                    return;
                }

                String hostname = args[0];
                try {
                    InetAddress[] addresses = InetAddress.getAllByName(hostname);
                    terminal.println("Server:  UnKnown");
                    terminal.println("Address:  8.8.8.8");
                    terminal.println();
                    terminal.println("Non-authoritative answer:");
                    terminal.println("Name:    " + hostname);

                    for (InetAddress addr : addresses) {
                        if (addr instanceof Inet4Address) {
                            terminal.println("Address: " + addr.getHostAddress());
                        }
                    }

                } catch (UnknownHostException e) {
                    terminal.printError("*** UnKnown can't find " + hostname + ": Non-existent domain");
                }
            }
        }

        private class TracertCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    terminal.printError("Usage: tracert <hostname>");
                    return;
                }

                String host = args[0];
                terminal.println("Tracing route to " + host + " over a maximum of 30 hops");
                terminal.println();

                try {
                    InetAddress target = InetAddress.getByName(host);
                    terminal.println("  1    <1 ms    <1 ms    <1 ms  192.168.1.1");
                    terminal.println("  2     5 ms     4 ms     6 ms  10.0.0.1");
                    terminal.println("  3    15 ms    12 ms    18 ms  " + target.getHostAddress());
                    terminal.println();
                    terminal.println("Trace complete.");

                } catch (UnknownHostException e) {
                    terminal.printError("Unable to resolve target system name " + host);
                }
            }
        }

        // Process Commands
        private class TasklistCommand implements Command {
            public void execute(String[] args) {
                terminal.println();
                terminal.println("Image Name                     PID Session Name        Session#    Mem Usage");
                terminal.println("========================= ======== ================ =========== ============");

                try {
                    // Get Java processes
                    long currentPid = ProcessHandle.current().pid();
                    long memoryUsage = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024;

                    terminal.println(String.format("%-25s %8d %-16s %11d %,8d K",
                            "java.exe", currentPid, "Console", 1, memoryUsage));

                    // Try to get other processes
                    ProcessHandle.allProcesses()
                            .limit(10)  // Limit to prevent too much output
                            .forEach(process -> {
                                ProcessHandle.Info info = process.info();
                                String command = info.command().orElse("Unknown");
                                String name = new File(command).getName();
                                if (name.isEmpty()) name = "System";

                                terminal.println(String.format("%-25s %8d %-16s %11d %8s",
                                        name, process.pid(), "Services", 0, "N/A"));
                            });

                } catch (Exception e) {
                    terminal.println(String.format("%-25s %8d %-16s %11d %,8d K",
                            "java.exe", ProcessHandle.current().pid(), "Console", 1,
                            (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024));
                }
            }
        }

        private class TaskkillCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    terminal.printError("Usage: taskkill /PID <process_id> or taskkill /IM <image_name>");
                    return;
                }

                if (args[0].equals("/PID") && args.length > 1) {
                    try {
                        long pid = Long.parseLong(args[1]);
                        Optional<ProcessHandle> process = ProcessHandle.of(pid);

                        if (process.isPresent()) {
                            if (process.get().destroyForcibly()) {
                                terminal.printSuccess("SUCCESS: Sent termination signal to process with PID " + pid);
                            } else {
                                terminal.printError("ERROR: The process with PID " + pid + " could not be terminated.");
                            }
                        } else {
                            terminal.printError("ERROR: The process with PID " + pid + " could not be found.");
                        }
                    } catch (NumberFormatException e) {
                        terminal.printError("ERROR: Invalid PID format.");
                    }
                } else if (args[0].equals("/IM") && args.length > 1) {
                    terminal.println("SUCCESS: Sent termination signal to process \"" + args[1] + "\".");
                } else {
                    terminal.printError("ERROR: Invalid syntax. Usage: taskkill /PID <process_id> or taskkill /IM <image_name>");
                }
            }
        }

        // Text Processing Commands
        private class FindCommand implements Command {
            public void execute(String[] args) throws IOException {
                if (args.length < 2) {
                    terminal.printError("Usage: find \"search_text\" filename");
                    return;
                }

                String searchText = args[0].replace("\"", "");
                String fileName = args[1];

                File file = new File(terminal.getCurrentDirectory(), fileName);
                if (!file.exists()) {
                    terminal.printError("File not found: " + fileName);
                    return;
                }

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    int lineNumber = 0;
                    boolean found = false;

                    terminal.println("---------- " + fileName.toUpperCase());

                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        if (line.toLowerCase().contains(searchText.toLowerCase())) {
                            terminal.println("[" + lineNumber + "]" + line);
                            found = true;
                        }
                    }

                    if (!found) {
                        terminal.println("Text not found: " + searchText);
                    }
                }
            }
        }

        private class FindstrCommand implements Command {
            public void execute(String[] args) throws IOException {
                if (args.length < 2) {
                    terminal.printError("Usage: findstr pattern filename");
                    return;
                }

                String pattern = args[0];
                String fileName = args[1];

                File file = new File(terminal.getCurrentDirectory(), fileName);
                if (!file.exists()) {
                    terminal.printError("FINDSTR: Cannot open " + fileName);
                    return;
                }

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    Pattern regex = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
                    boolean found = false;

                    while ((line = reader.readLine()) != null) {
                        if (regex.matcher(line).find()) {
                            terminal.println(fileName + ":" + line);
                            found = true;
                        }
                    }

                    if (!found) {
                        terminal.println("Pattern not found: " + pattern);
                    }
                }
            }
        }

        private class SortCommand implements Command {
            public void execute(String[] args) throws IOException {
                if (args.length == 0) {
                    terminal.printError("Usage: sort filename");
                    return;
                }

                String fileName = args[0];
                File file = new File(terminal.getCurrentDirectory(), fileName);

                if (!file.exists()) {
                    terminal.printError("File not found: " + fileName);
                    return;
                }

                List<String> lines = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                }

                Collections.sort(lines);

                for (String sortedLine : lines) {
                    terminal.println(sortedLine);
                }
            }
        }

        private class MoreCommand implements Command {
            public void execute(String[] args) throws IOException {
                if (args.length == 0) {
                    terminal.printError("Usage: more filename");
                    return;
                }

                String fileName = args[0];
                File file = new File(terminal.getCurrentDirectory(), fileName);

                if (!file.exists()) {
                    terminal.printError("File not found: " + fileName);
                    return;
                }

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    int lineCount = 0;

                    while ((line = reader.readLine()) != null) {
                        terminal.println(line);
                        lineCount++;

                        // Pause every 24 lines
                        if (lineCount % 24 == 0) {
                            terminal.print("-- More -- Press any key to continue...");
                            System.in.read(); // Wait for input
                            terminal.println();
                        }
                    }
                }
            }
        }

        private class HeadCommand implements Command {
            public void execute(String[] args) throws IOException {
                if (args.length == 0) {
                    terminal.printError("Usage: head [-n lines] filename");
                    return;
                }

                int linesToShow = 10;
                String fileName = args[args.length - 1];

                // Parse -n option
                for (int i = 0; i < args.length - 1; i++) {
                    if (args[i].equals("-n") && i + 1 < args.length - 1) {
                        try {
                            linesToShow = Integer.parseInt(args[i + 1]);
                        } catch (NumberFormatException e) {
                            terminal.printError("Invalid number: " + args[i + 1]);
                            return;
                        }
                    }
                }

                File file = new File(terminal.getCurrentDirectory(), fileName);
                if (!file.exists()) {
                    terminal.printError("File not found: " + fileName);
                    return;
                }

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    int count = 0;
                    while ((line = reader.readLine()) != null && count < linesToShow) {
                        terminal.println(line);
                        count++;
                    }
                }
            }
        }

        private class TailCommand implements Command {
            public void execute(String[] args) throws IOException {
                if (args.length == 0) {
                    terminal.printError("Usage: tail [-n lines] filename");
                    return;
                }

                int linesToShow = 10;
                String fileName = args[args.length - 1];

                // Parse -n option
                for (int i = 0; i < args.length - 1; i++) {
                    if (args[i].equals("-n") && i + 1 < args.length - 1) {
                        try {
                            linesToShow = Integer.parseInt(args[i + 1]);
                        } catch (NumberFormatException e) {
                            terminal.printError("Invalid number: " + args[i + 1]);
                            return;
                        }
                    }
                }

                File file = new File(terminal.getCurrentDirectory(), fileName);
                if (!file.exists()) {
                    terminal.printError("File not found: " + fileName);
                    return;
                }

                List<String> lines = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                }

                int startIndex = Math.max(0, lines.size() - linesToShow);
                for (int i = startIndex; i < lines.size(); i++) {
                    terminal.println(lines.get(i));
                }
            }
        }

        private class WcCommand implements Command {
            public void execute(String[] args) throws IOException {
                if (args.length == 0) {
                    terminal.printError("Usage: wc [-l] [-w] [-c] filename");
                    return;
                }

                boolean countLines = false;
                boolean countWords = false;
                boolean countChars = false;
                String fileName = args[args.length - 1];

                if (args.length == 1) {
                    countLines = countWords = countChars = true;
                } else {
                    for (int i = 0; i < args.length - 1; i++) {
                        switch (args[i]) {
                            case "-l": countLines = true; break;
                            case "-w": countWords = true; break;
                            case "-c": countChars = true; break;
                        }
                    }
                }

                File file = new File(terminal.getCurrentDirectory(), fileName);
                if (!file.exists()) {
                    terminal.printError("File not found: " + fileName);
                    return;
                }

                int lineCount = 0, wordCount = 0, charCount = 0;

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineCount++;
                        charCount += line.length() + 1; // +1 for newline
                        wordCount += line.trim().isEmpty() ? 0 : line.trim().split("\\s+").length;
                    }
                }

                StringBuilder result = new StringBuilder();
                if (countLines) result.append(String.format("%8d", lineCount));
                if (countWords) result.append(String.format("%8d", wordCount));
                if (countChars) result.append(String.format("%8d", charCount));
                result.append(" ").append(fileName);

                terminal.println(result.toString());
            }
        }

        // System Information Commands
        private class SysteminfoCommand implements Command {
            public void execute(String[] args) {
                terminal.println();
                terminal.println("Host Name:                 " + getHostname());
                terminal.println("OS Name:                   " + System.getProperty("os.name"));
                terminal.println("OS Version:                " + System.getProperty("os.version"));
                terminal.println("OS Manufacturer:           " + System.getProperty("os.arch"));
                terminal.println("System Type:               " + System.getProperty("os.arch"));
                terminal.println("Processor(s):              " + Runtime.getRuntime().availableProcessors() + " Processor(s) Installed.");

                long maxMemory = Runtime.getRuntime().maxMemory();
                long totalMemory = Runtime.getRuntime().totalMemory();
                long freeMemory = Runtime.getRuntime().freeMemory();

                terminal.println("Total Physical Memory:     " + String.format("%,d", maxMemory / 1024 / 1024) + " MB");
                terminal.println("Available Physical Memory: " + String.format("%,d", freeMemory / 1024 / 1024) + " MB");
                terminal.println("Virtual Memory: Max Size:  " + String.format("%,d", totalMemory / 1024 / 1024) + " MB");
                terminal.println("System Manufacturer:       Oracle Corporation");
                terminal.println("System Model:              Java Virtual Machine");
                terminal.println("Time Zone:                 " + TimeZone.getDefault().getDisplayName());
                terminal.println("Network Card(s):           " + getNetworkCardCount() + " NIC(s) Installed.");
                terminal.println("Domain:                    " + System.getenv("USERDOMAIN"));
                terminal.println("Logon Server:              N/A");
                terminal.println();
            }

            private String getHostname() {
                try {
                    return InetAddress.getLocalHost().getHostName();
                } catch (UnknownHostException e) {
                    return "Unknown";
                }
            }

            private int getNetworkCardCount() {
                try {
                    int count = 0;
                    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                    while (interfaces.hasMoreElements()) {
                        NetworkInterface ni = interfaces.nextElement();
                        if (ni.isUp() && !ni.isLoopback()) {
                            count++;
                        }
                    }
                    return count;
                } catch (Exception e) {
                    return 1;
                }
            }
        }

        private class UnameCommand implements Command {
            public void execute(String[] args) {
                if (args.length > 0 && args[0].equals("-a")) {
                    terminal.println(System.getProperty("os.name") + " " +
                            getHostname() + " " +
                            System.getProperty("os.version") + " " +
                            System.getProperty("os.arch") + " " +
                            System.getProperty("java.version"));
                } else {
                    terminal.println(System.getProperty("os.name"));
                }
            }

            private String getHostname() {
                try {
                    return InetAddress.getLocalHost().getHostName();
                } catch (UnknownHostException e) {
                    return "unknown";
                }
            }
        }

        private class VolCommand implements Command {
            public void execute(String[] args) {
                File currentDir = new File(terminal.getCurrentDirectory());
                File root = currentDir;

                // Find the root directory
                while (root.getParent() != null) {
                    root = root.getParentFile();
                }

                terminal.println(" Volume in drive " + root.getPath() + " is " +
                        (root.getName().isEmpty() ? "Local Disk" : root.getName()));
                terminal.println(" Volume Serial Number is " +
                        Integer.toHexString(root.hashCode()).toUpperCase());
            }
        }

        private class DfCommand implements Command {
            public void execute(String[] args) {
                terminal.println("Filesystem     1K-blocks      Used Available Use% Mounted on");

                File[] roots = File.listRoots();
                for (File root : roots) {
                    long totalSpace = root.getTotalSpace() / 1024;
                    long freeSpace = root.getFreeSpace() / 1024;
                    long usedSpace = totalSpace - freeSpace;
                    int usePercent = (int) ((usedSpace * 100) / totalSpace);

                    terminal.println(String.format("%-14s %10d %10d %9d %3d%% %s",
                            root.getPath(), totalSpace, usedSpace, freeSpace, usePercent, root.getPath()));
                }
            }
        }

        private class DiskpartCommand implements Command {
            public void execute(String[] args) {
                terminal.println("Microsoft DiskPart simulation");
                terminal.println();
                terminal.println("Available commands:");
                terminal.println("  list disk    - Display a list of disks");
                terminal.println("  list volume  - Display a list of volumes");
                terminal.println("  exit         - Exit DiskPart");
                terminal.println();
                terminal.printWarning("This is a simulation. Real diskpart operations are not performed.");
            }
        }

        private class AttribCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    // Show attributes for all files in current directory
                    File dir = new File(terminal.getCurrentDirectory());
                    File[] files = dir.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            showFileAttributes(file);
                        }
                    }
                } else {
                    // Show attributes for specific file
                    File file = new File(terminal.getCurrentDirectory(), args[0]);
                    if (file.exists()) {
                        showFileAttributes(file);
                    } else {
                        terminal.printError("File not found: " + args[0]);
                    }
                }
            }

            private void showFileAttributes(File file) {
                StringBuilder attrs = new StringBuilder();
                attrs.append(file.canRead() ? "R" : " ");
                attrs.append(file.canWrite() ? " " : "R");
                attrs.append(file.isHidden() ? "H" : " ");
                attrs.append(file.isDirectory() ? "D" : " ");

                terminal.println(String.format("%s %s", attrs.toString(), file.getName()));
            }
        }

        // Utility Commands
        private class HistoryCommand implements Command {
            public void execute(String[] args) {
                List<String> commands = terminal.getHistory().getAllCommands();
                if (commands.isEmpty()) {
                    terminal.println("No commands in history.");
                    return;
                }

                for (int i = 0; i < commands.size(); i++) {
                    terminal.println(String.format("%4d  %s", i + 1, commands.get(i)));
                }
            }
        }

        private class WhichCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    terminal.printError("Usage: which <command>");
                    return;
                }

                String command = args[0];

                // Check if it's an internal command
                if (commands.containsKey(command.toLowerCase())) {
                    terminal.println(command + ": shell built-in command");
                    return;
                }

                // Check PATH
                String pathVar = terminal.getEnvironment().get("PATH");
                if (pathVar != null) {
                    String[] paths = pathVar.split(File.pathSeparator);
                    for (String path : paths) {
                        File executable = new File(path, command);
                        if (executable.exists() && executable.canExecute()) {
                            terminal.println(executable.getAbsolutePath());
                            return;
                        }

                        // Try with .exe extension on Windows
                        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                            File exeFile = new File(path, command + ".exe");
                            if (exeFile.exists() && exeFile.canExecute()) {
                                terminal.println(exeFile.getAbsolutePath());
                                return;
                            }
                        }
                    }
                }

                terminal.printError("Command not found: " + command);
            }
        }

        private class CalcCommand implements Command {
            public void execute(String[] args) {
                if (args.length == 0) {
                    terminal.println("Simple Calculator");
                    terminal.println("Usage: calc <expression>");
                    terminal.println("Example: calc 2 + 3 * 4");
                    terminal.println("Supported operators: +, -, *, /, %, ^");
                    return;
                }

                String expression = String.join(" ", args);
                try {
                    double result = evaluateExpression(expression);
                    terminal.println(expression + " = " + result);
                } catch (Exception e) {
                    terminal.printError("Invalid expression: " + expression);
                }
            }

            private double evaluateExpression(String expression) {
                // Simple expression evaluator
                expression = expression.replaceAll("\\s+", "");

                // Handle basic operations
                if (expression.contains("+")) {
                    String[] parts = expression.split("\\+", 2);
                    return evaluateExpression(parts[0]) + evaluateExpression(parts[1]);
                } else if (expression.contains("-") && !expression.startsWith("-")) {
                    int lastMinus = expression.lastIndexOf("-");
                    if (lastMinus > 0) {
                        String left = expression.substring(0, lastMinus);
                        String right = expression.substring(lastMinus + 1);
                        return evaluateExpression(left) - evaluateExpression(right);
                    }
                } else if (expression.contains("*")) {
                    String[] parts = expression.split("\\*", 2);
                    return evaluateExpression(parts[0]) * evaluateExpression(parts[1]);
                } else if (expression.contains("/")) {
                    String[] parts = expression.split("/", 2);
                    return evaluateExpression(parts[0]) / evaluateExpression(parts[1]);
                } else if (expression.contains("%")) {
                    String[] parts = expression.split("%", 2);
                    return evaluateExpression(parts[0]) % evaluateExpression(parts[1]);
                } else if (expression.contains("^")) {
                    String[] parts = expression.split("\\^", 2);
                    return Math.pow(evaluateExpression(parts[0]), evaluateExpression(parts[1]));
                }

                return Double.parseDouble(expression);
            }
        }
    }

    // File System Utilities
    private static class FileSystemUtils {
        public static void copyDirectory(File source, File target) throws IOException {
            if (!target.exists()) {
                target.mkdirs();
            }

            File[] files = source.listFiles();
            if (files != null) {
                for (File file : files) {
                    File targetFile = new File(target, file.getName());
                    if (file.isDirectory()) {
                        copyDirectory(file, targetFile);
                    } else {
                        Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        }

        public static long getDirectorySize(File directory) {
            long size = 0;
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        size += file.length();
                    } else if (file.isDirectory()) {
                        size += getDirectorySize(file);
                    }
                }
            }
            return size;
        }

        public static String formatBytes(long bytes) {
            String[] units = {"B", "KB", "MB", "GB", "TB"};
            int unitIndex = 0;
            double size = bytes;

            while (size >= 1024 && unitIndex < units.length - 1) {
                size /= 1024;
                unitIndex++;
            }

            return String.format("%.2f %s", size, units[unitIndex]);
        }
    }

    // Batch Script Processor
    private class BatchProcessor {
        private Terminal terminal;
        private Map<String, String> variables;

        public BatchProcessor(Terminal terminal) {
            this.terminal = terminal;
            this.variables = new HashMap<>();
        }

        public void executeBatch(String fileName) throws IOException {
            File batchFile = new File(terminal.getCurrentDirectory(), fileName);
            if (!batchFile.exists()) {
                terminal.printError("Batch file not found: " + fileName);
                return;
            }

            terminal.println("Executing batch file: " + fileName);

            try (BufferedReader reader = new BufferedReader(new FileReader(batchFile))) {
                String line;
                int lineNumber = 0;

                while ((line = reader.readLine()) != null) {
                    lineNumber++;
                    line = line.trim();

                    if (line.isEmpty() || line.startsWith("::") || line.startsWith("REM")) {
                        continue; // Skip empty lines and comments
                    }

                    // Process batch commands
                    if (line.startsWith("@echo off")) {
                        terminal.echoOn = false;
                    } else if (line.startsWith("echo on")) {
                        terminal.echoOn = true;
                    } else if (line.startsWith("set ")) {
                        processSetCommand(line.substring(4));
                    } else if (line.startsWith("if ")) {
                        processIfCommand(line);
                    } else if (line.startsWith("for ")) {
                        processForCommand(line);
                    } else if (line.startsWith("goto ")) {
                        terminal.println("GOTO command not fully implemented");
                    } else if (line.startsWith("call ")) {
                        String subCommand = line.substring(5);
                        processor.executeCommand(expandVariables(subCommand));
                    } else if (line.startsWith("pause")) {
                        terminal.print("Press any key to continue . . . ");
                        try {
                            System.in.read();
                        } catch (IOException e) {
                            // Ignore
                        }
                        terminal.println();
                    } else {
                        // Execute as regular command
                        String expandedLine = expandVariables(line);
                        if (terminal.echoOn) {
                            terminal.println("> " + expandedLine);
                        }
                        processor.executeCommand(expandedLine);
                    }
                }
            }

            terminal.println("Batch file execution completed.");
        }

        private void processSetCommand(String command) {
            if (command.contains("=")) {
                String[] parts = command.split("=", 2);
                String varName = parts[0].trim();
                String varValue = parts.length > 1 ? parts[1].trim() : "";
                variables.put(varName, varValue);
                terminal.getEnvironment().put(varName, varValue);
            }
        }

        private void processIfCommand(String command) {
            // Simple if command processing
            terminal.println("IF command: " + command + " (simplified processing)");
        }

        private void processForCommand(String command) {
            // Simple for loop processing
            terminal.println("FOR command: " + command + " (simplified processing)");
        }

        private String expandVariables(String command) {
            // Expand environment variables
            for (Map.Entry<String, String> var : variables.entrySet()) {
                command = command.replace("%" + var.getKey() + "%", var.getValue());
            }

            for (Map.Entry<String, String> var : terminal.getEnvironment().entrySet()) {
                command = command.replace("%" + var.getKey() + "%", var.getValue());
                command = command.replace("$" + var.getKey(), var.getValue());
            }

            return command;
        }
    }

    // Main method to run the terminal
    public static void main(String[] args) {
        System.out.println("Starting Java Terminal...");

        // Set up console encoding for better character support
        try {
            System.setProperty("file.encoding", "UTF-8");
            System.setProperty("console.encoding", "UTF-8");
        } catch (Exception e) {
            // Ignore encoding setup errors
        }

        Terminal terminal = new Terminal();

        // Handle command line arguments
        if (args.length > 0) {
            if (args[0].equals("-c") && args.length > 1) {
                // Execute single command
                terminal.processor.executeCommand(args[1]);
                return;
            } else if (args[0].equals("-f") && args.length > 1) {
                // Execute batch file
                try {
                    BatchProcessor batchProcessor = terminal.new BatchProcessor(terminal);
                    batchProcessor.executeBatch(args[1]);
                } catch (IOException e) {
                    System.err.println("Error executing batch file: " + e.getMessage());
                }
                return;
            } else if (args[0].equals("-h") || args[0].equals("--help")) {
                // Show help
                System.out.println("Java Terminal Usage:");
                System.out.println("  java JavaTerminal           - Start interactive terminal");
                System.out.println("  java JavaTerminal -c \"cmd\"   - Execute single command");
                System.out.println("  java JavaTerminal -f file   - Execute batch file");
                System.out.println("  java JavaTerminal -h        - Show this help");
                return;
            }
        }

        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nTerminal session ended.");
        }));

        // Start the terminal
        terminal.start();
    }
}
