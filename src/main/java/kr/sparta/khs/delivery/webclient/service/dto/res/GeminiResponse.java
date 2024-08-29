// GeminiResponse.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package kr.sparta.khs.delivery.webclient.service.dto.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GeminiResponse {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;

}

