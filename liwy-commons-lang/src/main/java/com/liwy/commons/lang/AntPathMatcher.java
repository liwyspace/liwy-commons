package com.liwy.commons.lang;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Ant风格Path匹配模式<p/>
 *
 * <pre>
 *     `?`  匹配1个字符
 *     `*`  匹配0个或多个字符
 *     `**` 匹配路径中的0个或多个目录
 * </pre>
 *
 * <p>常用方法</p>
 * <ul>
 * <li><b>isMatch<b/>   - 路径字符串是否匹配pattern字符串</li>
 * </ul>
 *
 * @author liwy
 * @version v1.0.2
 */
public class AntPathMatcher {
    /**
     * 默认路径分隔符: "/"
     */
    public static final String DEFAULT_PATH_SEPARATOR = "/";

    /**
     * <p>是否匹配</p>
     *
     * @param pattern 通配符表达式
     * @param path    要匹配的路径
     * @return boolean 如果匹配返回true
     */
    public static boolean isMatch(String pattern, String path) {
        if (path.startsWith(DEFAULT_PATH_SEPARATOR) != pattern.startsWith(DEFAULT_PATH_SEPARATOR)) {
            return false;
        }

        String[] pattDirs = tokenizePath(pattern);
        String[] pathDirs = tokenizePath(path);

        int pattIdxStart = 0;
        int pattIdxEnd = pattDirs.length - 1;
        int pathIdxStart = 0;
        int pathIdxEnd = pathDirs.length - 1;

        // 匹配所有的元素直到第一个 **
        while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
            String pattDir = pattDirs[pattIdxStart];
            if ("**".equals(pattDir)) {
                break;
            }
            if (!matchStrings(pattDir, pathDirs[pathIdxStart])) {
                return false;
            }
            pattIdxStart++;
            pathIdxStart++;
        }

        // Path已遍历完所有元素
        if (pathIdxStart > pathIdxEnd) {
            // Patt已遍历完所有元素，既Patt元素个数等于Path
            if (pattIdxStart > pattIdxEnd) {
                return (pattern.endsWith(DEFAULT_PATH_SEPARATOR) ? path.endsWith(DEFAULT_PATH_SEPARATOR) :
                        !path.endsWith(DEFAULT_PATH_SEPARATOR));
            }
            // Patt到达最后一个元素，即Patt元素个数比Path多一个
            // 如果Patt最后一个元素是*或分隔符则可以匹配
            if (pattIdxStart == pattIdxEnd && pattDirs[pattIdxStart].equals("*") && path.endsWith(DEFAULT_PATH_SEPARATOR)) {
                return true;
            }

            // Patt剩余的元素只要有不为**的，就返回false
            for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
                if (!pattDirs[i].equals("**")) {
                    return false;
                }
            }
            return true;
        }
        // Patt已遍历完所有元素
        else if (pattIdxStart > pattIdxEnd) {
            return false;
        }

        // 匹配到最后一个 '**'
        while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
            String pattDir = pattDirs[pattIdxEnd];
            if (pattDir.equals("**")) {
                break;
            }
            if (!matchStrings(pattDir, pathDirs[pathIdxEnd])) {
                return false;
            }
            pattIdxEnd--;
            pathIdxEnd--;
        }
        // Path已遍历完所有元素
        if (pathIdxStart > pathIdxEnd) {
            // Patt剩余的元素只要有不为**的，就返回false
            for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
                if (!pattDirs[i].equals("**")) {
                    return false;
                }
            }
            return true;
        }

        // 遍历中间的部分，使用**分隔
        while (pattIdxStart != pattIdxEnd && pathIdxStart <= pathIdxEnd) {
            // Patt中第一个**的位置
            int patIdxTmp = -1;
            for (int i = pattIdxStart + 1; i <= pattIdxEnd; i++) {
                if (pattDirs[i].equals("**")) {
                    patIdxTmp = i;
                    break;
                }
            }
            // 如果第一个就是**跳过一个
            if (patIdxTmp == pattIdxStart + 1) {
                // '**/**' situation, so skip one
                pattIdxStart++;
                continue;
            }
            // Find the pattern between padIdxStart & padIdxTmp in str between
            // strIdxStart & strIdxEnd
            int patLength = (patIdxTmp - pattIdxStart - 1);
            int strLength = (pathIdxEnd - pathIdxStart + 1);
            int foundIdx = -1;

            // 在path中寻找匹配patt的串
            strLoop:
            for (int i = 0; i <= strLength - patLength; i++) {
                for (int j = 0; j < patLength; j++) {
                    String subPat = pattDirs[pattIdxStart + j + 1];
                    String subStr = pathDirs[pathIdxStart + i + j];
                    if (!matchStrings(subPat, subStr)) {
                        continue strLoop;
                    }
                }
                foundIdx = pathIdxStart + i;
                break;
            }

            if (foundIdx == -1) {
                return false;
            }

            pattIdxStart = patIdxTmp;
            pathIdxStart = foundIdx + patLength;
        }

        for (int i = pattIdxStart; i <= pattIdxEnd; i++) {
            if (!pattDirs[i].equals("**")) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>将路径通过路径分隔(/)符拆分成数组</p>
     *
     * @param path
     * @return java.lang.String[]
     */
    private static String[] tokenizePath(String path) {
        if (path == null) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(path, DEFAULT_PATH_SEPARATOR);
        List<String> tokens = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken().trim();
            if (token.length() > 0) {
                tokens.add(token);
            }
        }
        return tokens.toArray(new String[tokens.size()]);
    }

    private static boolean matchStrings(String pattern, String str) {
        return new AntPathStringMatcher(pattern).matchStrings(str);
    }

    private static class AntPathStringMatcher {
        private static final Pattern GLOB_PATTERN = Pattern.compile("\\?|\\*|\\{((?:\\{[^/]+?\\}|[^/{}]|\\\\[{}])+?)\\}");
        private static final String DEFAULT_VARIABLE_PATTERN = "(.*)";

        private final Pattern pattern;

        private final List<String> variableNames = new LinkedList<String>();

        public AntPathStringMatcher(String pattern) {
            StringBuilder patternBuilder = new StringBuilder();
            Matcher m = GLOB_PATTERN.matcher(pattern);
            int end = 0;
            while (m.find()) {
                patternBuilder.append(quote(pattern, end, m.start()));
                String match = m.group();
                if ("?".equals(match)) {
                    patternBuilder.append('.');
                } else if ("*".equals(match)) {
                    patternBuilder.append(".*");
                } else if (match.startsWith("{") && match.endsWith("}")) {
                    int colonIdx = match.indexOf(':');
                    if (colonIdx == -1) {
                        patternBuilder.append(DEFAULT_VARIABLE_PATTERN);
                        this.variableNames.add(m.group(1));
                    } else {
                        String variablePattern = match.substring(colonIdx + 1, match.length() - 1);
                        patternBuilder.append('(');
                        patternBuilder.append(variablePattern);
                        patternBuilder.append(')');
                        String variableName = match.substring(1, colonIdx);
                        this.variableNames.add(variableName);
                    }
                }
                end = m.end();
            }
            patternBuilder.append(quote(pattern, end, pattern.length()));
            this.pattern = Pattern.compile(patternBuilder.toString());
        }

        /**
         * <p>将正则字符串转换为字面量</p>
         *
         * @param s
         * @param start
         * @param end
         * @return java.lang.String
         */
        private String quote(String s, int start, int end) {
            if (start == end) {
                return "";
            }
            return Pattern.quote(s.substring(start, end));
        }

        public boolean matchStrings(String str) {
            Matcher matcher = this.pattern.matcher(str);
            return matcher.matches();
        }
    }
}
