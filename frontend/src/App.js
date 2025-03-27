import React, { useState } from "react";
import axios from "axios";

function App() {
    const [htmlContent, setHtmlContent] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const loadSchedule = async (fileType) => {
        setLoading(true);
        setError("");

        try {
            const response = await axios.get(`http://localhost:8080/file?fileType=${fileType}`, {
                headers: { Accept: "text/html" },
                responseType: "text",
            });

            setHtmlContent(response.data);
        } catch (err) {
            console.error("Failed to load schedule:", err);
            setError("Something went wrong while loading the schedule.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={{ padding: "20px" }}>
            <h2>Course Schedule</h2>
            <div style={{ marginBottom: "16px" }}>
                <button onClick={() => loadSchedule("excel")} style={{ marginRight: "10px" }}>
                    Load Excel
                </button>
                <button onClick={() => loadSchedule("pdf")}>Load PDF</button>
            </div>

            {loading && <p>Loading...</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}

            {/* Render HTML from backend */}
            <div dangerouslySetInnerHTML={{ __html: htmlContent }} />
        </div>
    );
}

export default App;
