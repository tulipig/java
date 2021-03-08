package com;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

//简单工厂类
interface IRuleConfigParser{
    void ruleConfigParse();
}
class JsonRuleConfigParser implements IRuleConfigParser{
    public JsonRuleConfigParser() {
        System.out.println("create JsonRuleConfigParser...");
    }

    @Override
    public void ruleConfigParse() {
        System.out.println("parse json...");
    }
}
class XmlRuleConfigParser implements IRuleConfigParser{
    public XmlRuleConfigParser() {
        System.out.println("create XmlRuleConfigParser...");
    }
    @Override
    public void ruleConfigParse() {
        System.out.println("parse xml...");
    }
}
class YamlRuleConfigParser implements IRuleConfigParser{
    public YamlRuleConfigParser() {
        System.out.println("create YamlRuleConfigParser...");
    }
    @Override
    public void ruleConfigParse() {
        System.out.println("parse yaml...");
    }
}
class PropertiesRuleConfigParser implements IRuleConfigParser{
    public PropertiesRuleConfigParser() {
        System.out.println("create PropertiesRuleConfigParser...");
    }
    @Override
    public void ruleConfigParse() {
        System.out.println("parse properties...");
    }
}

class RuleConfigParserFactory {
    private static final Map<String, IRuleConfigParser> cachedParsers = new HashMap<>();

    static {
        cachedParsers.put("json", new JsonRuleConfigParser());
        cachedParsers.put("xml", new XmlRuleConfigParser());
        cachedParsers.put("yaml", new YamlRuleConfigParser());
        cachedParsers.put("properties", new PropertiesRuleConfigParser());
    }

    public static IRuleConfigParser createParser(String configFormat) {
        if (configFormat == null || configFormat.isEmpty()) {
            return null;//返回null还是IllegalArgumentException全凭你自己说了算
        }
        IRuleConfigParser parser = cachedParsers.get(configFormat.toLowerCase());
        return parser;
    }
}

public class TestFactory {
    @Test
    void testRuleConfigParserFactory(){
        IRuleConfigParser iRuleConfigParser = RuleConfigParserFactory.createParser("yaml");
        iRuleConfigParser.ruleConfigParse();
    }

    @Test
    void testRuleConfigParserFactory2() throws InvalidRuleConfigException {
        IRuleConfigParser iRuleConfigParser = RuleConfigSource.load("test.yaml");
        iRuleConfigParser.ruleConfigParse();
    }
}

//-------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>

//工厂方法
interface IRuleConfigParserFactory {
    IRuleConfigParser createParser();
}

class JsonRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
        return new JsonRuleConfigParser();
    }
}

class XmlRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
        return new XmlRuleConfigParser();
    }
}

class YamlRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
        return new YamlRuleConfigParser();
    }
}

class PropertiesRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
        return new PropertiesRuleConfigParser();
    }
}

class InvalidRuleConfigException extends Exception {
    public InvalidRuleConfigException(String message){
        System.out.println(message);
    }
}

class RuleConfigSource {
    public static IRuleConfigParser load(String ruleConfigFilePath) throws InvalidRuleConfigException {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);

        IRuleConfigParserFactory parserFactory = null;
        if ("json".equalsIgnoreCase(ruleConfigFileExtension)) {
            parserFactory = new JsonRuleConfigParserFactory();
        } else if ("xml".equalsIgnoreCase(ruleConfigFileExtension)) {
            parserFactory = new XmlRuleConfigParserFactory();
        } else if ("yaml".equalsIgnoreCase(ruleConfigFileExtension)) {
            parserFactory = new YamlRuleConfigParserFactory();
        } else if ("properties".equalsIgnoreCase(ruleConfigFileExtension)) {
            parserFactory = new PropertiesRuleConfigParserFactory();
        } else {
            throw new InvalidRuleConfigException("Rule config file format is not supported: " + ruleConfigFilePath);
        }
        IRuleConfigParser parser = parserFactory.createParser();
        return parser;
    }

    private static String getFileExtension(String filePath) {
        //...解析文件名获取扩展名，比如rule.json，返回json
        return "yaml";
    }
}


