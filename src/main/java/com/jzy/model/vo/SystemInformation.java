package com.jzy.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author JinZhiyun
 * @version 1.0
 * @ClassName SystemInformation
 * @description 系统信息的vo
 * @date 2019/12/7 9:06
 **/
@Data
public class SystemInformation implements Serializable {
    private static final long serialVersionUID = 6996126191457669269L;

    private int cpu;

    private int memory;

    private int disk;

    public SystemInformation() {
    }

    public SystemInformation(int cpu, int memory, int disk) {
        this.cpu = cpu;
        this.memory = memory;
        this.disk = disk;
    }

    public SystemInformation(double cpu, double memory, double disk) {
        this.cpu = (int) cpu;
        this.memory = (int) memory;
        this.disk = (int) disk;
    }
}
