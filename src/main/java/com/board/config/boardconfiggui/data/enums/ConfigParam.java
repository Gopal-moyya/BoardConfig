package com.board.config.boardconfiggui.data.enums;

public enum ConfigParam {

    POOL_COUNT {
        @Override
        public String getParam() {
            return "poolCount";
        }

        @Override
        public String getDisplayValue() {
            return "Pool Count";
        }

        @Override
        public ViewType getViewType() {
            return ViewType.TEXT_FIELD;
        }
    },
    POLLING_BIT_INDEX {
        @Override
        public String getParam() {
            return "pollingBitIndex";
        }

        @Override
        public String getDisplayValue() {
            return "Polling Bit Index";
        }

        @Override
        public ViewType getViewType() {
            return ViewType.TEXT_FIELD;
        }
    },
    OPCODE {
        @Override
        public String getParam() {
            return "opcode";
        }

        @Override
        public String getDisplayValue() {
            return "OpCode";
        }

        @Override
        public ViewType getViewType() {
            return ViewType.TEXT_FIELD;
        }
    },
    IS_DISABLED {
        @Override
        public String getParam() {
            return "isDisabled";
        }

        @Override
        public String getDisplayValue() {
            return "Is Disabled";
        }

        @Override
        public ViewType getViewType() {
            return ViewType.SPINNER;
        }
    },
    POLLING_POLARITY {
        @Override
        public String getParam() {
            return "pollingPolarity";
        }

        @Override
        public String getDisplayValue() {
            return "Polling Polarity";
        }

        @Override
        public ViewType getViewType() {
            return ViewType.TEXT_FIELD;
        }
    },
    REPETITION_DELAY {
        @Override
        public String getParam() {
            return "repetitionDelay";
        }

        @Override
        public String getDisplayValue() {
            return "Repetition Delay";
        }

        @Override
        public ViewType getViewType() {
            return ViewType.TEXT_FIELD;
        }
    };

    public abstract String getParam();
    public abstract String getDisplayValue();
    public abstract ViewType getViewType();
}
