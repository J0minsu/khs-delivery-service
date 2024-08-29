package kr.sparta.khs.delivery.webclient.service.dto.res;

public class UsageMetadata {
    private Long candidatesTokenCount;
    private Long totalTokenCount;
    private Long promptTokenCount;

    public Long getCandidatesTokenCount() { return candidatesTokenCount; }
    public void setCandidatesTokenCount(long value) { this.candidatesTokenCount = value; }

    public Long getTotalTokenCount() { return totalTokenCount; }
    public void setTotalTokenCount(long value) { this.totalTokenCount = value; }

    public Long getPromptTokenCount() { return promptTokenCount; }
    public void setPromptTokenCount(long value) { this.promptTokenCount = value; }
}
