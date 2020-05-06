package com.viettel.im.database.BO;

/**
 * CableBox entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CableBox implements java.io.Serializable {

    // Fields
    private Long cableBoxId;
    private Long boardId;
    private String code;
    private String name;
    private String address;
    private Long maxPorts;
    private Long usedPorts;
    private Long status;
    private Long x;
    private Long y;
    private Long dslamId;
    private Long reservedPort;
    private String type;
    private String dslamcode;
    private String boardscode;
    private String BoardsName;

    // Constructors
    /** default constructor */
    public CableBox() {
    }

    /** minimal constructor */
    public CableBox(Long cableBoxId, String code, String name, String address,
            Long maxPorts, Long usedPorts, Long status) {
        this.cableBoxId = cableBoxId;
        this.code = code;
        this.name = name;
        this.address = address;
        this.maxPorts = maxPorts;
        this.usedPorts = usedPorts;
        this.status = status;
    }

    /** full constructor */
    public CableBox(Long cableBoxId, Long boardId, String code, String name,
            String address, Long maxPorts, Long usedPorts, Long status,
            Long x, Long y, Long dslamId, Long reservedPort, String type) {
        this.cableBoxId = cableBoxId;
        this.boardId = boardId;
        this.code = code;
        this.name = name;
        this.address = address;
        this.maxPorts = maxPorts;
        this.usedPorts = usedPorts;
        this.status = status;
        this.x = x;
        this.y = y;
        this.dslamId = dslamId;
        this.reservedPort = reservedPort;
        this.type = type;
    }

    public CableBox(Long cableBoxId, Long boardId, String code, String name,
            String address, Long maxPorts, Long usedPorts, Long status,
            Long x, Long y, Long dslamId, Long reservedPort, String type, String boardscode) {
        this.cableBoxId = cableBoxId;
        this.boardId = boardId;
        this.code = code;
        this.name = name;
        this.address = address;
        this.maxPorts = maxPorts;
        this.usedPorts = usedPorts;
        this.status = status;
        this.x = x;
        this.y = y;
        this.dslamId = dslamId;
        this.reservedPort = reservedPort;
        this.type = type;
        this.boardscode = boardscode;
    }

    public CableBox(Long cableBoxId, String code, String name,
            String address, Long maxPorts, Long usedPorts, Long status,
            Long x, Long y, Long dslamId, Long reservedPort, String type, String dslamCode) {
        this.cableBoxId = cableBoxId;
        //this.boardId = boardId;
        this.code = code;
        this.name = name;
        this.address = address;
        this.maxPorts = maxPorts;
        this.usedPorts = usedPorts;
        this.status = status;
        this.x = x;
        this.y = y;
        this.dslamId = dslamId;
        this.reservedPort = reservedPort;
        this.type = type;
        this.dslamcode = dslamCode;
    }

    // Property accessors
    public Long getCableBoxId() {
        return this.cableBoxId;
    }

    public void setCableBoxId(Long cableBoxId) {
        this.cableBoxId = cableBoxId;
    }

    public Long getBoardId() {
        return this.boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getMaxPorts() {
        return this.maxPorts;
    }

    public void setMaxPorts(Long maxPorts) {
        this.maxPorts = maxPorts;
    }

    public Long getUsedPorts() {
        return this.usedPorts;
    }

    public void setUsedPorts(Long usedPorts) {
        this.usedPorts = usedPorts;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getX() {
        return this.x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return this.y;
    }

    public void setY(Long y) {
        this.y = y;
    }

    public Long getDslamId() {
        return this.dslamId;
    }

    public void setDslamId(Long dslamId) {
        this.dslamId = dslamId;
    }

    public Long getReservedPort() {
        return this.reservedPort;
    }

    public void setReservedPort(Long reservedPort) {
        this.reservedPort = reservedPort;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBoardscode() {
        return boardscode;
    }

    public void setBoardscode(String boardscode) {
        this.boardscode = boardscode;
    }

    public String getBoardsName() {
        return BoardsName;
    }

    public void setBoardsName(String BoardsName) {
        this.BoardsName = BoardsName;
    }

    public String getDslamcode() {
        return dslamcode;
    }

    public void setDslamcode(String dslamcode) {
        this.dslamcode = dslamcode;
    }
}
