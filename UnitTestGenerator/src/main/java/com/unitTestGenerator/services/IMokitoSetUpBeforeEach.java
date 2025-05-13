package com.unitTestGenerator.services;

public interface IMokitoSetUpBeforeEach {

    default String getMokitoSetUpBeforeEach(boolean isAutoCloseable){
        StringBuilder mokitoSetUpBeforeEach = new StringBuilder("\n");
        if(isAutoCloseable) {
            mokitoSetUpBeforeEach.append("\t").append("private AutoCloseable closeable;").append("\n").append("\n");
            mokitoSetUpBeforeEach.append("\t").append("\t@BeforeEach").append("\n");
            mokitoSetUpBeforeEach.append("\t").append("\tpublic void setUp() {").append("\n");
            mokitoSetUpBeforeEach.append("\t").append("\t\tcloseable = MockitoAnnotations.openMocks(this);").append("\n").append("}").append("\n").append("\n");
            mokitoSetUpBeforeEach.append("\t").append("\t@AfterEach").append("\n");
            mokitoSetUpBeforeEach.append("\t").append("\tpublic void tearDown() throws Exception {").append("\n");
            mokitoSetUpBeforeEach.append("\t").append("\t\tcloseable.close();").append("\n").append("}").append("\n").append("\n");
        }else {
            mokitoSetUpBeforeEach.append("\t").append("\t@BeforeEach").append("\n");
            mokitoSetUpBeforeEach.append("\t").append("\tpublic void setUp() {").append("\n");
            mokitoSetUpBeforeEach.append("\t").append("\t\tMockitoAnnotations.openMocks(this);").append("\n").append("\t").append("}").append("\n").append("\n");
        }
        return mokitoSetUpBeforeEach.toString();
    }
}
